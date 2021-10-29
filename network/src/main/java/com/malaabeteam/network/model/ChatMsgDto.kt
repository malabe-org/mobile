package com.malaabeteam.network.model

data class ChatMsgDto(
  val id: Int,
  val chatId: Int,
  val isRead: Boolean?,
  val isDeleted: Boolean?,
  val message: String?,
  val messageDate: String?,
  val user: UserDto?
)
