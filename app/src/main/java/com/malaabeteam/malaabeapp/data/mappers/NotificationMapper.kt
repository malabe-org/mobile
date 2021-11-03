package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.data.model.UserNotifications
import com.malaabeteam.network.model.UserNotificationDto
import javax.inject.Inject

class NotificationMapper @Inject constructor(
  private val userMapper: UserMapper
){
  fun fromNetwork(notifications: UserNotificationDto)=
    UserNotifications(
      notifications = notifications.notifications
    )

}
