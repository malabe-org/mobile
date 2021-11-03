package com.malaabeteam.malaabeapp.ui.main.browse.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.malaabeteam.malaabeapp.Config
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.ui.common.BaseAdapter
import com.malaabeteam.network.model.RequestUser
import kotlinx.android.synthetic.main.view_document.view.*

class DocAdapter(var mListDocuments: List<DocumentListItem>?=null):  RecyclerView.Adapter<DocAdapter.ViewHolder>(){

  fun setItems(listDoc: List<DocumentListItem>){
    this.mListDocuments = listDoc
  }
  inner class ViewHolder internal constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var viewDocumentImage: ImageView
    var viewDocumentDate: TextView
    var viewDocumentTeamIcon: ImageView
    var viewDocumentTitle: TextView
    var viewDocumentDescription: TextView
    var viewDocumentListPrice: TextView
    var viewDocumentStateText: TextView
    var viewDocumentProgress: ProgressBar
    init {
      // Define click listener for the ViewHolder's View.
      viewDocumentImage = itemView.findViewById(R.id.viewDocumentImage)
      viewDocumentDate = itemView.findViewById(R.id.viewDocumentDate)
      viewDocumentTeamIcon = itemView.findViewById(R.id.viewDocumentTeamIcon)
      viewDocumentTitle = itemView.findViewById(R.id.viewDocumentTitle)
      viewDocumentDescription = itemView.findViewById(R.id.viewDocumentDescription)
      viewDocumentListPrice = itemView.findViewById(R.id.viewDocumentListPrice)
      viewDocumentStateText = itemView.findViewById(R.id.viewDocumentStateText)
      viewDocumentProgress = itemView.findViewById(R.id.viewDocumentProgress)
    }
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.view_document, parent, false)

    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val doc = mListDocuments?.get(position)?.document
    holder.viewDocumentListPrice.setBackgroundResource(when(doc?.treatment!!.isOpen) {
      true -> R.drawable.bg_success_state
      false ->R.drawable.bg_pending_state
    })
    bindImage(doc, holder.viewDocumentImage)
  }
  private fun bindImage(item: RequestUser, imgV: ImageView) {
    Glide.with(imgV.context)
      .load(item.documents.seekerPhoto)
      .placeholder(R.drawable.image_placeholder)
      .transform(CenterCrop())
      .error(R.drawable.ic_innovations)
      .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
      .transition(DrawableTransitionOptions.withCrossFade(Config.DEFAULT_IMAGE_FADE_DURATION))
      .into(imgV)
  }

  fun getDrawableResIdByName(iv: ImageView, resName: String): Int {
    val pkgName: String = iv.context.packageName
    return iv.context.resources.getIdentifier(resName, "drawable", pkgName)
  }

  override fun getItemCount(): Int {
    return mListDocuments?.size ?: 0
  }

}
