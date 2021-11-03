package com.malaabeteam.malaabeapp.ui.main.profile

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.model.User
import com.malaabeteam.malaabeapp.data.repository.UserRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.utilities.extensions.toMultiPartBody
import com.malaabeteam.persistance.exception.NotAuthorizedException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ProfileViewData(val user: User)

class ProfileViewModel @Inject constructor(
  private val userRepository: UserRepository
) : BaseViewModel<ProfileUiModel>() {

  fun loadProfile(userId: Long? = null) {
    viewModelScope.launch {
      try {
        uiState = ProfileUiModel(isLoading = true)

        // Unfortunately we need to fetch reviews just to get user global rating
        val user = coroutineScope {
          val userRequest = async { userRepository.getUser() }
          userRequest.await()
        }

        val allData = ProfileViewData(
          user = user.copy()
        )

        uiState = ProfileUiModel(
          isLoading = false,
          user = allData.user
        )
      } catch (t: Throwable) {
        onError(t)
      }
    }
  }

  fun reloadTeamsCount() {
    viewModelScope.launch {
      try {
        val user = userRepository.getUser()
        uiState = ProfileUiModel()
      } catch (t: Throwable) {
      }
    }
  }

  fun uploadPicture(picture: Bitmap) {
    viewModelScope.launch {
      try {
        uiState = ProfileUiModel(loadingPicture = true)

        //userRepository.changePicture(picture.toMultiPartBody())

        uiState = ProfileUiModel(loadingPicture = false, pictureLoaded = picture)
      } catch (e: Exception) {
        onError(e)
      }
    }
  }

  fun logout(): Boolean {
    var b: Boolean = false
    viewModelScope.launch{
      try {
        uiState = ProfileUiModel(isLoading = true)
        userRepository.logout()
        b = true
        uiState = ProfileUiModel(isLoading = false)
      } catch (t: Throwable) {
        onError(t)
        b = false
      }
    }
    return b
  }

  private fun onError(error: Throwable) {
    uiState = ProfileUiModel(
      isLoading = false,
      notAuthorized = error is NotAuthorizedException
    )
    _errorLiveData.value = R.string.errorGeneral
    Timber.e(error)
  }
}
