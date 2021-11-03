package com.malaabeteam.malaabeapp.ui.login.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.malaabeteam.malaabeapp.R
import kotlinx.android.synthetic.main.item_presentation.view.*

data class PresentationItem(
  @StringRes val titleText: Int,
  @StringRes val descriptionText: Int,
  @DrawableRes val background: Int
)

class PresentationAdapter: RecyclerView.Adapter<PresentationAdapter.PresentationViewHolder>(){

  private val presentationElements = listOf(
    PresentationItem(R.string.presentationTextPage1Title, R.string.presentationTextPage1Description, R.drawable.bg_main_gradient),
    PresentationItem(R.string.presentationTextPage2Title, R.string.presentationTextPage2Description, R.drawable.bg_main_gradient),
    PresentationItem(R.string.presentationTextPage3Title, R.string.presentationTextPage3Description, R.drawable.bg_main_gradient),
    PresentationItem(R.string.presentationTextPage4Title, R.string.presentationTextPage4Description, R.drawable.bg_main_gradient)
  )

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PresentationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_presentation, parent, false))

  override fun onBindViewHolder(holder: PresentationAdapter.PresentationViewHolder, position: Int) = holder.bind(presentationElements[position])

  override fun getItemCount() = presentationElements.size

  inner class PresentationViewHolder constructor(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: PresentationItem) {
      with(view) {
        presentationItemTitle.text = context.getString(item.titleText)
        presentationItemDescription.text = context.getString(item.descriptionText)
        presentationBackground.background = ContextCompat.getDrawable(context, item.background)
      }
    }
  }
}
