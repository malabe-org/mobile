package com.malaabeteam.network.model

data class ChatDto(
  val id: Int,
  val applicant: UserDto?,
  val agent: UserDto?,
  val chatDate: String?,
  val lastUpdate: String?,
  val totalUnread: Int?,
  val totalMsgs: Int?,
  val messages: List<ChatMsgDto>?
)
