package com.malaabeteam.malaabeapp.data.model

data class Notification(
  val requestId: String,
  val userId: String,
  val state: Boolean,
  val decision: String,
  val moreInfo: String,
  val recuperationAddress: String
)
