package com.malaabeteam.malaabeapp.ui.common

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class BaseAdapter<Item : ListItem> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  abstract val asyncDiffer: AsyncListDiffer<Item>

  var itemClickListener: (Item) -> Unit = { }

  fun setItems(newItems: List<Item>){
    asyncDiffer.submitList(newItems)
  }

  override fun getItemCount() = asyncDiffer.currentList.size

  fun getItems(): List<Item> = asyncDiffer.currentList

  class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
