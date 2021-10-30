package com.malaabeteam.malaabeapp.utilities.extensions

import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.malaabeteam.malaabeapp.R

fun BottomNavigationView.setBadgeVisibility(menuItemId: Int, isVisible: Boolean) {
  getOrCreateBadge(menuItemId).apply {
    verticalOffset = dimenToPx(R.dimen.bottomNavigationBasketBadgeTopOffset)
    horizontalOffset = dimenToPx(R.dimen.bottomNavigationBasketBadgeTopOffset)
    backgroundColor = ContextCompat.getColor(context, R.color.colorAccent)
    this.isVisible = isVisible
  }
}
