package com.malaabeteam.network.model

data class NotificationDto(
  val requestId: String,
  val userId: String,
  val state: Boolean,
  val decision: String,
  val moreInfo: String,
  val recuperationAddress: String
)
