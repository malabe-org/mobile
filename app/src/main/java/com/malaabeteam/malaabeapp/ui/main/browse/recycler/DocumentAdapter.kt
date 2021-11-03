package com.malaabeteam.malaabeapp.ui.main.browse.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.malaabeteam.malaabeapp.ui.common.BaseAdapter
import com.malaabeteam.malaabeapp.ui.main.browse.views.DocumentView
import org.w3c.dom.Document
import timber.log.Timber

class DocumentAdapter: BaseAdapter<DocumentListItem>() {
  override val asyncDiffer = AsyncListDiffer(this, DocumentItemDiffCallback())

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    BaseViewHolder(DocumentView(parent.context))
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = asyncDiffer.currentList[position]
    Timber.d("------------REALLY? $item")
    (holder.itemView as DocumentView).bind(item, itemClickListener)
  }
}
