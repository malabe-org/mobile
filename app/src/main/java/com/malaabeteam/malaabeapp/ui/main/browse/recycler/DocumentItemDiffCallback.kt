package com.malaabeteam.malaabeapp.ui.main.browse.recycler

import androidx.recyclerview.widget.DiffUtil

class DocumentItemDiffCallback : DiffUtil.ItemCallback<DocumentListItem>() {

  override fun areItemsTheSame(oldItem: DocumentListItem, newItem: DocumentListItem) =
    oldItem.document.id == newItem.document.id

  override fun areContentsTheSame(oldItem: DocumentListItem, newItem: DocumentListItem) =
    oldItem.document == newItem.document && oldItem.isLoading == newItem.isLoading
}
