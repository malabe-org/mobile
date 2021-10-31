package com.malaabeteam.network.api

import com.malaabeteam.network.model.request.ChatMessageBody
import com.malaabeteam.network.service.ChatService
import javax.inject.Inject

class ChatApi @Inject constructor(
  private val service: ChatService
) {

  suspend fun fetchChats(
    session: String,
    pageSize: Int,
    first: Int
  ) = service.fetchChats(session, pageSize, first)

  suspend fun fetchChatMessages(
    chatId: Int,
    session: String,
    pageSize: Int,
    first: Int
  ) = service.fetchChatMessages(chatId, session, pageSize, first)

  suspend fun fetchUserChatMessages(
    userId: String,
    session: String,
    pageSize: Int,
    first: Int
  ) = service.fetchUserChatMessages(userId, session, pageSize, first)

  suspend fun searchChats(
    session: String,
    searchText: String,
    pageSize: Int,
    first: Int
  ) = service.searchChats(session, searchText, pageSize, first)

  suspend fun readAll(
    session: String,
    chatId: Int
  ) = service.readAll(chatId, session)

  suspend fun postMessage(
    session: String,
    message: String,
    userId: String,
    chatId: Int?
  ) = service.postMessage(userId, session, ChatMessageBody(chatId, message)).response
}
