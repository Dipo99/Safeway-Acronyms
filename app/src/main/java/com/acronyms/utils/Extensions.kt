package com.acronyms.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.acronyms.R


fun Activity.showErrorDialog(message: String, isFinish: Boolean = false) {
    AlertDialog.Builder(this).setMessage(message).setCancelable(false)
        .setNeutralButton(this.getString(R.string.ALERT_BUTTON_CLOSE)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            if (isFinish) {
                finish()
            }
        }.show()
}

fun Activity.isInternetConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}