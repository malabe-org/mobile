package com.malaabeteam.network.model

data class ImageDto(
  val imageId: Long,
  val imageUrl: String? = "",
  val description: String? = "",
  val created: String? = "",
  val isBlocked: Boolean? = false
)
