package com.malaabeteam.network.model.response

data class ListResponse<T>(
  val success: Boolean,
  val message: String,
  val paylod: List<T>?
)
