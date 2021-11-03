package com.malaabeteam.network.api

import com.malaabeteam.network.service.UserNotificationService
import javax.inject.Inject

class UserNotificationApi @Inject constructor(
  private val service: UserNotificationService
){
  suspend fun fetchUserNotification(token: String, userId:String) =
    service.fetchUserNotification("Bearer $token", userId)
}
