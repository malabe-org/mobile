package com.malaabeteam.malaabeapp.ui.main

import androidx.lifecycle.viewModelScope
import com.google.firebase.iid.FirebaseInstanceId
import com.malaabeteam.malaabeapp.data.repository.DocumentRepository
import com.malaabeteam.malaabeapp.data.repository.UserRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.persistance.UserSession
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val documentRepository: DocumentRepository,
  private val userRepository: UserRepository,
  private val userSession: UserSession
) : BaseViewModel<MainUiModel>() {

  fun checkItemsInDocument() {
    viewModelScope.launch {
      try {
        uiState = MainUiModel(hasDocInProcess = documentRepository.hasDocInProcess())
      } catch (t: Throwable) {
        Timber.e(t)
      }
    }
  }

  fun updateDeviceId() {
    if (!userSession.isAuthorized()) return
    FirebaseInstanceId.getInstance().instanceId
      .addOnCompleteListener {
        viewModelScope.launch {
          try {
            it.result?.token?.let {
              Timber.d(it)
              userRepository.updateDeviceId(it)
            }
          } catch (error: Throwable) {
            Timber.e("Failed to send FCM token to backend.")
          }
        }
      }
      .addOnFailureListener {
        Timber.e("Failed to retrieve FCM token.")
      }
  }
}
