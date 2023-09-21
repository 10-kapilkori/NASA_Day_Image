package com.example.nasadayimage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nasadayimage.R
import com.example.nasadayimage.databinding.CustomNasaDataListItemsBinding
import com.example.nasadayimage.pojos.DayDataItems
import com.example.nasadayimage.staticmethods.Helper
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DayDataItemsAdapter(
    var context: Context,
    var dayDataItemList: List<DayDataItems>
) : RecyclerView.Adapter<DayDataItemsAdapter.DayDataItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayDataItemViewHolder {
//      Initializing the View
        val view = CustomNasaDataListItemsBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.custom_nasa_data_list_items, parent, false)
        )

        return DayDataItemViewHolder(view)
    }

    override fun getItemCount(): Int = dayDataItemList.size

    override fun onBindViewHolder(holder: DayDataItemViewHolder, position: Int) {
        holder.onBind(dayDataItemList[position])
    }

    inner class DayDataItemViewHolder(private val nasaDataListItemsBinding: CustomNasaDataListItemsBinding) :
        RecyclerView.ViewHolder(nasaDataListItemsBinding.root) {

        fun onBind(dayDataItemList: DayDataItems) {
//            Checking if the data item is an image or video
            if (dayDataItemList.mediaType == "image") {
                Picasso.get()
                    .load(dayDataItemList.imgUrl ?: dayDataItemList.url)
                    .placeholder(Helper.getDrawablePlaceHolder(context))
//                        Resizing the image for better caching for Picasso
                    .resize(500, 500)
                    .into(nasaDataListItemsBinding.ndiImageIv)

//                Changing visibility for Image View and Web View
                nasaDataListItemsBinding.ndiImageTitleTv.visibility = VISIBLE
                nasaDataListItemsBinding.ndiVideoWv.visibility = GONE
                nasaDataListItemsBinding.ndiImageIv.visibility = VISIBLE
            } else {
                nasaDataListItemsBinding.ndiImageTitleTv.visibility = GONE
                nasaDataListItemsBinding.ndiVideoWv.visibility = VISIBLE
                nasaDataListItemsBinding.ndiImageIv.visibility = GONE

//                Using iFrame Embeds for playing the videos uploaded to youtube
                val url =
                    "<iframe width=\"100%\" height=\"100%\" src=\"${dayDataItemList.url}\" title=\"${dayDataItemList.imgTitle}\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope;\" allowfullscreen></iframe>"
                nasaDataListItemsBinding.ndiVideoWv.loadData(url, "text/html", "utf-8")
                nasaDataListItemsBinding.ndiVideoWv.settings.javaScriptEnabled = true
                nasaDataListItemsBinding.ndiVideoWv.webChromeClient = WebChromeClient()
            }

            nasaDataListItemsBinding.ndiImageTitleTv.text =
                dayDataItemList.imgTitle

            nasaDataListItemsBinding.ndiImageDescriptionTv.text =
                dayDataItemList.description

            /**
             * The below code is used fetch the date to a certain format (01st OCT, 2023)
             * */
            val formattedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val postedDate = dayDataItemList.date

//            Fetching the date from the "date" parameter
            val date = SimpleDateFormat("dd", Locale.getDefault())
                .format(formattedDate.parse(postedDate.plus("T00:00:00:000"))!!)
//            Fetching the date from the "year" parameter
            val year = SimpleDateFormat("yyyy", Locale.getDefault())
                .format(formattedDate.parse(postedDate.plus("T00:00:00:000"))!!)

            /**
             * Creating a Calendar instance for fetching the month name
             * */
            val cal = Calendar.getInstance()
            cal.set(
                Calendar.MONTH,
                SimpleDateFormat("MM", Locale.getDefault()).format(
                    formattedDate.parse(
                        postedDate.plus("T00:00:00:000")
                    )!!
                ).toInt()
            )

            val month = SimpleDateFormat("MMMM", Locale.getDefault()).format(cal.time)

//            Simply putting all the pieces together to get the desired date format
            nasaDataListItemsBinding.ndiImagePostedDateTv.text =
                HtmlCompat.fromHtml(
                    "$date<sup>${
                        if (date[date.length - 1] == '1' && date[0] != '1')
                            context.getString(R.string.first_date)
                        else if (date[date.length - 1] == '2' && date[0] != '1')
                            context.getString(R.string.second_date)
                        else if (date[date.length - 1] == '3' && date[0] != '1')
                            context.getString(R.string.third_date)
                        else
                            context.getString(R.string.other_date)
                    }</sup> ${month.substring(0, 3).uppercase()}, $year",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
        }
    }
}