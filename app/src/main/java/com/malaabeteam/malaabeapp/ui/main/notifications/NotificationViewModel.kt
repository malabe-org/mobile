package com.malaabeteam.malaabeapp.ui.main.notifications

import androidx.lifecycle.viewModelScope
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.repository.NotificationRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.ui.main.browse.BrowseUiModel
import com.malaabeteam.malaabeapp.ui.main.notifications.recycler.NotificationListItem
import com.malaabeteam.persistance.UserSession
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
  private val notificationRepository: NotificationRepository,
  private val session : UserSession
): BaseViewModel<NotificationUiModel>() {

  fun loadNotifs(){
    viewModelScope.launch{
      try {
        uiState = NotificationUiModel(isLoading = true)
        val notifications = notificationRepository.getUserNotifications(session = session.token()!!, userId = session.id()!!).let{ it ->
          it.notifications.forEach { it2 ->
            NotificationListItem(it2)
          }
        }

      }catch (e: Throwable){
        onError(e)
      }

    }
  }

  private fun onError(error: Throwable) {
    uiState = NotificationUiModel(isLoading = false)
    _errorLiveData.value = R.string.errorGeneral
    Timber.w(error)
  }
}
