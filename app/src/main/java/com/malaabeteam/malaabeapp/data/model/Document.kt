package com.malaabeteam.malaabeapp.data.model

import org.threeten.bp.LocalDateTime

data class Document(
  val id: String,
  val title: String,
  val typeDocument: String,
  val imageUrl: String,
  val created: LocalDateTime,
  val captures: List<Image>,
  val description: String,
  val state: Boolean
){
  fun getMainImageUrl() = captures.firstOrNull()?.imageUrl ?: ""
}
