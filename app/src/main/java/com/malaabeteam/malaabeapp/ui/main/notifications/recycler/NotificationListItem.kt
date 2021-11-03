package com.malaabeteam.malaabeapp.ui.main.notifications.recycler

import com.malaabeteam.malaabeapp.ui.common.ListItem
import com.malaabeteam.network.model.NotificationDto

data class NotificationListItem(
  val notifications: NotificationDto,
  override val isLoading: Boolean = false
): ListItem
