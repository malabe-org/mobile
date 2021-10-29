package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.Config.DATE_FORMAT
import com.malaabeteam.malaabeapp.data.model.Chat
import com.malaabeteam.malaabeapp.data.model.ChatMsg
import com.malaabeteam.malaabeapp.data.model.User
import com.malaabeteam.network.model.ChatDto
import com.malaabeteam.network.model.ChatMsgDto
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale.ENGLISH
import javax.inject.Inject

class ChatMapper @Inject constructor(
  private val userMapper: UserMapper
) {

  private val dateFormatter by lazy { DateTimeFormatter.ofPattern(DATE_FORMAT, ENGLISH) }

  fun fromNetwork(dto: ChatDto) = Chat(
    id = dto.id,
    applicant = dto.applicant?.let { userMapper.fromNetwork(it) } ?: User.UNKNOWN,
    agent = dto.agent?.let { userMapper.fromNetwork(it) } ?: User.UNKNOWN,
    date = LocalDateTime.parse(dto.chatDate, dateFormatter),
    lastUpdate = LocalDateTime.parse(dto.lastUpdate, dateFormatter),
    totalMessages = dto.totalMsgs ?: 0,
    totalUnread = dto.totalUnread ?: 0,
    messages = dto.messages
      ?.map { mapChatMessage(it) }
      ?: emptyList()
  )

  fun mapChatMessage(dto: ChatMsgDto) = ChatMsg(
    id = dto.id,
    chatId = dto.chatId,
    isRead = dto.isRead ?: true,
    isDeleted = dto.isDeleted ?: false,
    message = dto.message ?: "",
    messageDate = LocalDateTime.parse(dto.messageDate, dateFormatter),
    user = dto.user?.let { user -> userMapper.fromNetwork(user) } ?: User.UNKNOWN
  )
}
