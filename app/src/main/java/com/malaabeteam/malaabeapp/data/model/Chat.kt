package com.malaabeteam.malaabeapp.data.model

import org.threeten.bp.LocalDateTime

data class Chat(
  val id: Int,
  val applicant: User,
  val agent: User,
  val date: LocalDateTime,
  val lastUpdate: LocalDateTime,
  val totalUnread: Int,
  val totalMessages: Int,
  val messages: List<ChatMsg>
)
