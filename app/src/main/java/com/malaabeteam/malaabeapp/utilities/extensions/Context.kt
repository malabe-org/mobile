package com.malaabeteam.malaabeapp.utilities.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.app.Activity
import android.view.View
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import timber.log.Timber

fun View.dimenToPx(@DimenRes dimenResId: Int) = resources.getDimensionPixelSize(dimenResId)

fun Activity.dimenToPx(@DimenRes dimenResId: Int) = resources.getDimensionPixelSize(dimenResId)

fun Fragment.dimenToPx(@DimenRes dimenResId: Int) = resources.getDimensionPixelSize(dimenResId)

fun Context.browse(url: String) {
  try {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
  } catch (e: ActivityNotFoundException) {
    Timber.e(e)
  }
}
