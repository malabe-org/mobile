package com.malaabeteam.malaabeapp.ui.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.malaabeapp.R
import kotlinx.android.synthetic.main.view_document_option.view.*

class DocumentOptionView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
  FrameLayout(context, attrs, defStyleAttr) {

  init {
    inflate(context, R.layout.view_document_option, this)
  }

  fun onSellClicked(onSellClicked: () -> Unit) {
    sellOptionRoot.onClick { onSellClicked() }
  }

  fun setState(isEnabled: Boolean) {
    sellOptionBg.isEnabled = isEnabled
  }
}
