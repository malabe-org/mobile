package com.malaabeteam.malaabeapp.ui.main.browse.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import com.google.android.material.tabs.TabLayout
import com.malaabeteam.malaabeapp.ui.common.behaviour.ScrollableViewBehaviour

class ScrollableTabLayout : TabLayout, CoordinatorLayout.AttachedBehavior {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  override fun getBehavior() = ScrollableViewBehaviour()

  override fun setEnabled(enabled: Boolean) {
    (getChildAt(0) as ViewGroup).forEach { it.setOnTouchListener { _, _ -> !enabled } }
  }
}
