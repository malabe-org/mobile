package com.malaabeteam.network.model

import org.threeten.bp.LocalDateTime

data class DocumentDto(
  val id: String,
  val title: String,
  val typeDocument: String,
  val imageUrl: String,
  val created: LocalDateTime,
  val captures: List<ImageDto>,
  val description: String,
  val state: Boolean
)
