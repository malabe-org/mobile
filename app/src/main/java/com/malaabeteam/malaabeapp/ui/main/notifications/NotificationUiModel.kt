package com.malaabeteam.malaabeapp.ui.main.notifications

import com.malaabeteam.malaabeapp.ui.common.UiModel
import com.malaabeteam.malaabeapp.ui.main.notifications.recycler.NotificationListItem

data class NotificationUiModel(
  val isLoading: Boolean? = null,
  val notifications: List<NotificationListItem>? = null
): UiModel() {
  override fun update(newModel: UiModel) =
    (newModel as NotificationUiModel).copy(
      isLoading = newModel.isLoading ?: isLoading,
      notifications = newModel.notifications ?: notifications
    )
}
