package com.malaabeteam.malaabeapp.data.model

import org.threeten.bp.LocalDateTime

data class ChatMsg(
  val id: Int,
  val chatId: Int,
  val isRead: Boolean,
  val isDeleted: Boolean,
  val message: String,
  val messageDate: LocalDateTime,
  val user: User
)
