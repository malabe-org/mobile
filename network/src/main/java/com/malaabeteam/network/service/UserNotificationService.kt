package com.malaabeteam.network.service

import com.malaabeteam.network.model.UserNotificationDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserNotificationService {
  @GET("notifications/{id}")
  suspend fun fetchUserNotification(
    @Header("Authorization") token: String,
    @Path("userId") userId: String
  ): UserNotificationDto
}
