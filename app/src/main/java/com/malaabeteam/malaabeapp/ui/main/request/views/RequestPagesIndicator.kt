package com.malaabeteam.malaabeapp.ui.main.request.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage
import kotlinx.android.synthetic.main.view_request_pages_indicator.view.*

class RequestPagesIndicator: LinearLayout {
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  init {
    inflate(context, R.layout.view_request_pages_indicator, this)
    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    orientation = VERTICAL
    clipChildren = false

    viewRequestIndicatorPage1.onClick {onPageClickListener?.invoke(RequestPage.DESCRIPTION)}
    viewRequestIndicatorPage2.onClick {onPageClickListener?.invoke(RequestPage.DOCUMENTS)}
    viewRequestIndicatorPage3.onClick {onPageClickListener?.invoke(RequestPage.END)}
  }

  private val pulseInterpolator by lazy { FastOutSlowInInterpolator() }
  private var animator: Animator? = null
  var onPageClickListener: ((RequestPage) -> Unit)? = null

  fun setPage(page: RequestPage) = when (page) {
    RequestPage.DOCUMENTS -> {
      viewRequestIndicatorProgress.animate().scaleX(0F).start()
      viewRequestIndicatorCheck1.setBackgroundResource(R.drawable.bg_circle_accent)
      viewRequestIndicatorCheck2.setBackgroundResource(R.drawable.bg_circle_gray)
      viewRequestIndicatorCheck3.setBackgroundResource(R.drawable.bg_circle_gray)
      startPulse(viewRequestIndicatorCheck1)
    }
    RequestPage.DESCRIPTION -> {
      viewRequestIndicatorProgress.animate().scaleX(0.5F).start()
      viewRequestIndicatorCheck1.setBackgroundResource(R.drawable.bg_circle_accent)
      viewRequestIndicatorCheck2.setBackgroundResource(R.drawable.bg_circle_accent)
      viewRequestIndicatorCheck3.setBackgroundResource(R.drawable.bg_circle_gray)
      startPulse(viewRequestIndicatorCheck2)
    }
    RequestPage.END -> {
      viewRequestIndicatorProgress.animate().scaleX(1F).start()
      viewRequestIndicatorCheck1.setBackgroundResource(R.drawable.bg_circle_accent)
      viewRequestIndicatorCheck2.setBackgroundResource(R.drawable.bg_circle_accent)
      viewRequestIndicatorCheck3.setBackgroundResource(R.drawable.bg_circle_accent)
      startPulse(viewRequestIndicatorCheck3)
    }
  }

  private fun startPulse(view: View) {
    clearPulse()
    animator?.cancel()
    animator = ObjectAnimator.ofPropertyValuesHolder(
      view,
      PropertyValuesHolder.ofFloat("scaleX", 1.2f, 0.8f),
      PropertyValuesHolder.ofFloat("scaleY", 1.2f, 0.8f)
    ).apply {
      duration = 1000
      interpolator = pulseInterpolator
      repeatCount = ObjectAnimator.INFINITE
      repeatMode = ObjectAnimator.REVERSE
      start()
    }
  }

  private fun clearPulse() {
    listOf(
      viewRequestIndicatorCheck1,
      viewRequestIndicatorCheck2,
      viewRequestIndicatorCheck3
    ).forEach {
      it.animate().scaleX(1F).start()
      it.animate().scaleY(1F).start()
    }
  }
}
