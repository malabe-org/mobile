package com.malaabeteam.malaabeapp.ui.main.notifications.recycler

import android.content.Context

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import com.malaabeteam.malaabeapp.R

class NotificationAdapter(val context: Context, var notificationList: List<NotificationListItem>?) : RecyclerView.Adapter<NotificationAdapter.Viewholder>() {

  fun setItems(listNotifs: List<NotificationListItem>){
    notificationList = listNotifs
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
    // to inflate the layout for each item of recycler view.
    val view: View = LayoutInflater.from(parent.context).inflate(R.layout.notifs_item, parent, false)
    return Viewholder(view)
  }

  override fun onBindViewHolder(holder: NotificationAdapter.Viewholder, position: Int) {
    // to set data to textview and imageview of each card layout
    val model = notificationList?.get(position)
    holder.courseNameTV.text = "Notification $position"
    holder.courseIV.setImageResource(R.drawable.ic_info_notif)
    holder.courseRatingTV.text = "You can get it from the commissariat of Dakar"
  }

  override fun getItemCount(): Int {
    // this method is used for showing number
    // of card items in recycler view.
    return notificationList?.size ?: 0
  }

  // View holder class for initializing of
  // your views such as TextView and Imageview.
  inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val courseIV: ImageView
    val courseNameTV: TextView
    val courseRatingTV: TextView

    init {
      courseIV = itemView.findViewById(R.id.idIVNotificationImage)
      courseNameTV = itemView.findViewById(R.id.idTVNotificationName)
      courseRatingTV = itemView.findViewById(R.id.idTVNotificationRating)
    }
  }
}
