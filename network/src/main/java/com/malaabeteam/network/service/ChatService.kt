package com.malaabeteam.network.service

import com.malaabeteam.network.model.ChatDto
import com.malaabeteam.network.model.ChatMsgDto
import com.malaabeteam.network.model.request.ChatMessageBody
import com.malaabeteam.network.model.response.ItemResponse
import com.malaabeteam.network.model.response.ListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatService {

  /**
   * Returns a list of chats for the logged user
   */
  @GET("chat")
  suspend fun fetchChats(
    @Query("session") token: String,
    @Query("pageSize") pageSize: Int,
    @Query("first") first: Int
  ): ListResponse<ChatDto>

  /**
   * Returns a list of chat messages for the given chat id
   */
  @GET("chat/messages/{chatId}")
  suspend fun fetchChatMessages(
    @Path("chatId") chatId: Int,
    @Query("session") token: String,
    @Query("pageSize") pageSize: Int,
    @Query("first") first: Int
  ): ListResponse<ChatMsgDto>

  /**
   * Returns a list of messages between the given user id and the logged user
   */
  @GET("chat/user/{_id}")
  suspend fun fetchUserChatMessages(
    @Path("_id") userId: String,
    @Query("session") token: String,
    @Query("pageSize") pageSize: Int,
    @Query("first") first: Int
  ): ListResponse<ChatMsgDto>

  /**
   * Returns a list of chats for the logged user
   */
  @GET("chat/search")
  suspend fun searchChats(
    @Query("session") token: String,
    @Query("searchText") searchText: String,
    @Query("pageSize") pageSize: Int,
    @Query("first") first: Int
  ): ListResponse<ChatMsgDto>

  /**
   * Mark all messages as read
   */
  @PUT("chat/read/all/{chatId}")
  suspend fun readAll(
    @Path("chatId") chatId: Int,
    @Query("session") token: String
  ): Any

  /**
   * Post message
   */
  @POST("chat/{_id}")
  suspend fun postMessage(
    @Path("_id") userId: String,
    @Query("session") token: String,
    @Body body: ChatMessageBody
  ): ItemResponse<ChatMsgDto>
}
