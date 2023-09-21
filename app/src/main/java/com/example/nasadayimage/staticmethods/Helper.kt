package com.example.nasadayimage.staticmethods

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.LayoutInflater
import android.view.Window
import com.example.nasadayimage.R
import com.example.nasadayimage.databinding.LoadingDisplayBinding
import com.example.nasadayimage.staticmethods.Constants.PLACE_HOLDER_VALUE
import com.example.nasadayimage.staticmethods.Constants.PROJECT_NAME

// A helper class to access some static methods
object Helper {

    //    Network Availability method
    fun isNetworkAvailable(context: Context): Boolean {
        var isOnline = false
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
            isOnline =
                capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isOnline
    }

    //    Fetching the preferences
    private fun getPreference(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            PROJECT_NAME, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(PLACE_HOLDER_VALUE, null).toString()
    }

    //    Storing the preferences
    private fun savePreference(context: Context, value: String?) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            PROJECT_NAME, Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(PLACE_HOLDER_VALUE, value)
        editor.apply()
    }

    //    Fetching the saved Preferences for the place holders
    fun getDrawablePlaceHolder(context: Context): Int {
        val placeHolderValue = getPreference(context)
        var intPlaceHolder = 0

        if (placeHolderValue.isNotEmpty() && placeHolderValue != "null") {
            intPlaceHolder = placeHolderValue.toInt() % 10
        }

        when (intPlaceHolder) {
            0 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_1
            }

            1 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_2
            }

            2 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_3
            }

            3 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_4
            }

            4 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_5
            }

            5 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_6
            }

            6 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_7
            }

            7 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_8
            }

            8 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_9
            }

            9 -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_10
            }

            else -> {
                intPlaceHolder += 1
                savePreference(context, intPlaceHolder.toString())
                return R.drawable.draw_place_holder_rect_1
            }
        }
    }

    //    Method to display an animated progress bar
    fun displayProgressDialog(context: Context): Dialog {
        val dialogLoaderBinding = LoadingDisplayBinding.inflate(LayoutInflater.from(context))
        val progressDialog = Dialog(context)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(dialogLoaderBinding.root)

        dialogLoaderBinding.pbLoadingDisplayProgressBar.setAnimation(R.raw.loader_anim)
        dialogLoaderBinding.pbLoadingDisplayProgressBar.playAnimation()
        return progressDialog
    }
}