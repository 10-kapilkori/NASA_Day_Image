package com.example.nasadayimage

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.nasadayimage.adapter.DayDataItemsAdapter
import com.example.nasadayimage.database.DayDataItemEntity
import com.example.nasadayimage.database.RoomDB
import com.example.nasadayimage.databinding.ActivityMainBinding
import com.example.nasadayimage.modelfactory.DayDataItemModelFactory
import com.example.nasadayimage.modelfactory.DayDataItemsLocallyModelFactory
import com.example.nasadayimage.pojos.DayDataItems
import com.example.nasadayimage.repository.DayDataItemLocalRepository
import com.example.nasadayimage.repository.DayDataItemRepository
import com.example.nasadayimage.retrofit.RetrofitClient
import com.example.nasadayimage.retrofit.Status
import com.example.nasadayimage.staticmethods.Helper
import com.example.nasadayimage.viewmodel.DayDataItemViewModel
import com.example.nasadayimage.viewmodel.DayDataItemsLocallyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: DayDataItemViewModel
    private lateinit var dbViewModel: DayDataItemsLocallyViewModel
    private lateinit var dayDataItemsAdapter: DayDataItemsAdapter
    private lateinit var loadingDialog: Dialog

    //    This variable stores the data items which are to be displayed inside the recycler view
    private val dataListItems = arrayListOf<DayDataItems>()

    private val roomDb by lazy { RoomDB.getDatabase(this) }
    private val repository by lazy { DayDataItemLocalRepository(roomDb.getDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        loadingDialog = Helper.displayProgressDialog(this)
        loadingDialog.show()

        setupViewModel()
        setupObserver()
        setupLocalObserver()

//        Making a network call to the database to fetch the data items
        dbViewModel.fetchDataItemsLocally()

//        Initializing the adapter
        dayDataItemsAdapter =
            DayDataItemsAdapter(this@MainActivity, dataListItems)
        activityMainBinding.mainDataItemsRv.adapter = dayDataItemsAdapter

//        Refresh event for user, when pulled down the data refreshes accordingly
        activityMainBinding.mainRootSrl.setOnRefreshListener {
            dbViewModel.fetchDataItemsLocally()
        }
    }

    private fun setupLocalObserver() {
//        Fetching the current date to check if the data is up to date or not in the database
        val currentDate =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)

//        Observing the observer for changes
        dbViewModel.mutableLiveDataForFetchItems.observe(this) {
            if (it.isNotEmpty()) {
                dataListItems.clear()

//                Adding the fetched list to the array list
                for (i in it) {
                    dataListItems.add(
                        DayDataItems(
                            date = i.postedDate,
                            description = i.description,
                            imgTitle = i.title,
                            imgUrl = i.imgUrl,
                            mediaType = i.mediaType,
                            url = i.imgUrl,
                        )
                    )
                }

//                Check for current and database latest date if available then notifying the adapter
//                otherwise making a network class from the latest date (database) and current date
                if (it[0].postedDate != currentDate)
                    mainViewModel.fetchDayDataItems(it[0].postedDate, currentDate)
                else {
                    dayDataItemsAdapter.notifyDataSetChanged()
                    activityMainBinding.mainRootSrl.isRefreshing = false
                    loadingDialog.dismiss()
                }
            } else {
                fetchDayDataItems()
            }
        }
    }

    private fun setupViewModel() {
//        Initializing the view models
        mainViewModel = ViewModelProvider(
            this,
            DayDataItemModelFactory(DayDataItemRepository(RetrofitClient.apiInterface))
        )[DayDataItemViewModel::class.java]

        dbViewModel = ViewModelProvider(
            this,
            DayDataItemsLocallyModelFactory(repository)
        )[DayDataItemsLocallyViewModel::class.java]
    }

    private fun fetchDayDataItems() {
//        Fetching the start date and end date to fetch the data for the current month
        val startDate =
            "${SimpleDateFormat("yyyy", Locale.getDefault()).format(Calendar.getInstance().time)}-${
                SimpleDateFormat("MM", Locale.getDefault()).format(Calendar.getInstance().time)
            }-01"
        val endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (Helper.isNetworkAvailable(this)) {
            mainViewModel.fetchDayDataItems(startDate, endDate)
        } else {
//            Showing a alert dialog if the network is not available
            showAlertDialog(
                getString(R.string.warning_title),
                getString(R.string.no_internet_connection)
            )
        }
    }

    private fun setupObserver() {
//        Observing the observer for the API class
        mainViewModel.mutableLiveData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    loadingDialog.show()
                }

                Status.FAILURE -> {
                    showAlertDialog(
                        getString(R.string.warning_title), getString(R.string.unexpected_event)
                    )
                    loadingDialog.dismiss()
                }

                Status.SUCCESS -> {
//                    Checking if the array list is not empty and removing the first element from
//                    the fetched data as that date will already be
//                    available in the database
                    if (dataListItems.isNotEmpty()) {
                        it.data?.removeAt(0)
                    }

//                    Iterating over the list and inserting the data to the database
                    for (i in it.data!!) {
                        dbViewModel.insertDataItemsLocally(
                            DayDataItemEntity(
                                title = i.imgTitle!!,
                                description = i.description!!,
                                imgUrl = (i.imgUrl ?: i.url) ?: "",
                                mediaType = i.mediaType!!,
                                postedDate = i.date!!
                            )
                        )

//                        Adding the data to first index for the latest output
                        dataListItems.add(0, i)
                    }

                    activityMainBinding.mainRootSrl.isRefreshing = false
                    dayDataItemsAdapter.notifyDataSetChanged()
                    loadingDialog.dismiss()
                }
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.retry) { _, _ ->
            fetchDayDataItems()
        }
        builder.show()
    }
}
