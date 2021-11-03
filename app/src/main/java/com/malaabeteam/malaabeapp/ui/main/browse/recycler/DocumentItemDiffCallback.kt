package com.malaabeteam.malaabeapp.ui.main.browse.recycler

import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber

class DocumentItemDiffCallback : DiffUtil.ItemCallback<DocumentListItem>() {

  override fun areItemsTheSame(oldItem: DocumentListItem, newItem: DocumentListItem) =
  oldItem.document._id == newItem.document._id

  override fun areContentsTheSame(oldItem: DocumentListItem, newItem: DocumentListItem) =
    oldItem.document == newItem.document && oldItem.isLoading == newItem.isLoading
}
