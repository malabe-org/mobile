package com.malaabeteam.malaabeapp.data.repository

import com.malaabeteam.malaabeapp.data.mappers.Mappers
import com.malaabeteam.malaabeapp.data.model.Notification
import com.malaabeteam.malaabeapp.data.model.UserNotifications
import com.malaabeteam.malaabeapp.di.scope.AppScope
import com.malaabeteam.network.MalaabeApi
import com.malaabeteam.network.model.UserNotificationDto
import com.malaabeteam.persistance.UserSession
import javax.inject.Inject

@AppScope
class NotificationRepository @Inject constructor(
  private val api: MalaabeApi,
  private val session: UserSession,
  private val mappers: Mappers
) {

  suspend fun getUserNotifications(session: String, userId: String): UserNotificationDto {
    val result = api.notification.fetchUserNotification(session, userId)
    result.let{
      mappers.notification.fromNetwork(it)
    }
    return result
  }



}
