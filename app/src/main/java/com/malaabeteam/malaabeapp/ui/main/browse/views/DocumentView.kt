package com.malaabeteam.malaabeapp.ui.main.browse.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.malaabeteam.common_ui.extensions.fadeIn
import com.malaabeteam.common_ui.extensions.gone
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.malaabeapp.Config.DEFAULT_IMAGE_FADE_DURATION
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.ui.main.browse.recycler.DocumentListItem
import com.malaabeteam.malaabeapp.utilities.DateUtilities
import kotlinx.android.synthetic.main.view_document.view.*

@SuppressLint("SetTextI18n")
class DocumentView : FrameLayout {
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  init {
    inflate(context, R.layout.view_document, this)
    layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    viewDocumentImage.clipToOutline = true
  }

  fun bind(item: DocumentListItem, itemClickListener: (DocumentListItem) -> Unit){
    clear()
    viewDocumentRoot.onClick { itemClickListener(item)}
    viewDocumentTitle.text = item.document.title
    viewDocumentDescription.text = item.document.description
    viewDocumentDate.text = DateUtilities.getTimeAgoString(item.document.created, context)
    viewDocumentListPrice.text = when(item.document.state) {
      true -> "success"
      false -> "pending"
    }
    viewDocumentListPrice.background = when(item.document.state) {
      true -> resources?.getDrawable(R.drawable.bg_success_state)
      false ->resources?.getDrawable(R.drawable.bg_pending_state)
    }
    viewDocumentProgress.run { if (item.isLoading) fadeIn() else gone() }
    bindImage(item)
  }

  private fun bindImage(item: DocumentListItem) {
    Glide.with(this)
      .load(item.document.getMainImageUrl())
      .placeholder(R.drawable.image_placeholder)
      .transform(CenterCrop())
      .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
      .transition(withCrossFade(DEFAULT_IMAGE_FADE_DURATION))
      .into(viewDocumentImage)
  }

  private fun clear() {
    Glide.with(this).clear(viewDocumentImage)
  }

}
