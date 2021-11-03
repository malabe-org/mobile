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
import org.threeten.bp.LocalDateTime
import timber.log.Timber

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

  fun bind(item: DocumentListItem,
           itemClickListener: (DocumentListItem) -> Unit
  ){
    Timber.d("-------------DOCUMENTVIEW -- $item")
    clear()
    viewDocumentRoot.onClick { Timber.d("itemClickListener(item)")}
    viewDocumentTitle.text = "Demande de passport"
    viewDocumentDescription.text = "Je voudrais un passport pour moi-même"
    viewDocumentDate.text = "21/11/2021"
    viewDocumentListPrice.text = when(item.document.treatment.isOpen) {
      true -> "success"
      false -> "pending"
    }
//    viewDocumentListPrice.background = when(item.document.treatment.isOpen) {
//      true -> resources?.getDrawable(R.drawable.bg_success_state)
//      false ->resources?.getDrawable(R.drawable.bg_pending_state)
//    }
    viewDocumentProgress.run { if (item.isLoading) fadeIn() else gone() }
    bindImage(item)
  }

  private fun bindImage(item: DocumentListItem) {

    Glide.with(this)
      .load(R.drawable.ic_doc_iconv2)
      .placeholder(R.drawable.com_facebook_auth_dialog_cancel_background)
      .transform(CenterCrop())
      .error(R.drawable.ic_innovations)
      .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
      .transition(withCrossFade(DEFAULT_IMAGE_FADE_DURATION))
      .into(viewDocumentImage)

    Glide.with(this)
      .load(when(item.document.treatment.isOpen){
        false ->R.drawable.ic_v2_check_success
        true -> R.drawable.ic_pending_state2
      })
      .transform(CenterCrop())
      .apply(RequestOptions.errorOf(R.drawable.ic_innovations))
      .transition(withCrossFade(DEFAULT_IMAGE_FADE_DURATION))
      .into(viewDocumentTeamIcon)
  }

  private fun clear() {
    Glide.with(this).clear(viewDocumentImage)
  }

}
