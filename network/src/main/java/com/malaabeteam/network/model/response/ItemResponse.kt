package com.malaabeteam.network.model.response

data class ItemResponse<T>(
  val success: Boolean,
  val message: String,
  val response: T
)
