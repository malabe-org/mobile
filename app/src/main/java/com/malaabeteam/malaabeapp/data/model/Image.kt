package com.malaabeteam.malaabeapp.data.model

import org.threeten.bp.LocalDateTime

data class Image(
  val id: Long,
  val imageUrl: String,
  val description: String,
  val created: LocalDateTime,
  val isBlocked: Boolean
)
