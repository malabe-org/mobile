package com.malaabeteam.malaabeapp.ui.main.request.views

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.malaabeteam.common_ui.extensions.gone
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.common_ui.extensions.visible
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.ui.main.request.helpers.DocumentFormData
import kotlinx.android.synthetic.main.view_request_add_another_doc.view.*
import timber.log.Timber

class AddAnotherDocumentView : FrameLayout {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  init {
    inflate(context, R.layout.view_request_add_another_doc, this)
    setBackgroundResource(R.drawable.bg_request_add_doc)
    clipToOutline = true
    layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
  }

  var onRemoveClickListener: ((DocumentFormData) -> Unit)? = null
  private var doc: DocumentFormData? = null

  fun setPhoto(doc: DocumentFormData) {
    this.doc = doc

    viewAddAnotherDocEmpty.gone()
    viewAddAnotherDocImageRemove.visible()
    viewAddAnotherDocImageRemove.onClick { onRemoveClickListener?.invoke(this.doc!!) }
    Glide.with(this)
      .load(doc.image)
      .error(R.mipmap.ic_launcher_round)
      .into(viewAddAnotherDocImage)
  }
}
