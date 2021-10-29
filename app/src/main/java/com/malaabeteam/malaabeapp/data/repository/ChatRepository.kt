package com.malaabeteam.malaabeapp.data.repository

import com.malaabeteam.malaabeapp.data.mappers.Mappers
import com.malaabeteam.malaabeapp.data.model.Chat
import com.malaabeteam.malaabeapp.data.model.ChatMsg
import com.malaabeteam.malaabeapp.di.scope.AppScope
import com.malaabeteam.network.MalaabeApi
import com.malaabeteam.persistance.UserSession
import javax.inject.Inject

@AppScope
class ChatRepository @Inject constructor(
  private val api: MalaabeApi,
  private val session: UserSession,
  private val mappers: Mappers
) {

  companion object {
    private const val PAGE_SIZE = 100
  }

  suspend fun loadChats(page: Int = 1): List<Chat> {
    val token = session.checkAuthorization()
    return api.chat.fetchChats(
      token,
      PAGE_SIZE,
      (page * PAGE_SIZE) - PAGE_SIZE
    ).paylod?.map {
      mappers.chat.fromNetwork(it)
    } ?: emptyList()
  }

  suspend fun loadChatMessages(page: Int = 1, chatId: Int): List<ChatMsg> {
    val token = session.checkAuthorization()
    return api.chat.fetchChatMessages(
      chatId,
      token,
      PAGE_SIZE,
      (page * PAGE_SIZE) - PAGE_SIZE
    ).paylod?.map {
      mappers.chat.mapChatMessage(it)
    } ?: emptyList()
  }

  suspend fun loadUserChatMessages(page: Int = 1, userId: String): List<ChatMsg> {
    val token = session.checkAuthorization()
    return api.chat.fetchUserChatMessages(
      userId,
      token,
      PAGE_SIZE,
      (page * PAGE_SIZE) - PAGE_SIZE
    ).paylod?.map {
      mappers.chat.mapChatMessage(it)
    } ?: emptyList()
  }

  suspend fun searchChats(page: Int = 1, searchText: String): List<ChatMsg> {
    val token = session.checkAuthorization()
    return api.chat.searchChats(
      token,
      searchText,
      PAGE_SIZE,
      (page * PAGE_SIZE) - PAGE_SIZE
    ).paylod?.map {
      mappers.chat.mapChatMessage(it)
    } ?: emptyList()
  }

  suspend fun readAll(chatId: Int) {
    val token = session.checkAuthorization()
    api.chat.readAll(token, chatId)
  }

  suspend fun postMessage(message: String, userId: String, chatId: Int?): ChatMsg {
    val token = session.checkAuthorization()
    val result = api.chat.postMessage(token, message, userId, chatId)
    return mappers.chat.mapChatMessage(result)
  }
}
