package com.malaabeteam.malaabeapp.utilities.extensions

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager

fun Activity.hideStatusBar() {
  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
  window.statusBarColor = Color.TRANSPARENT
  window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}
