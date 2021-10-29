package com.malaabeteam.malaabeapp.ui.login.signIn

import androidx.lifecycle.viewModelScope
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.repository.LoginRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.utilities.ErrorParser
import com.malaabeteam.malaabeapp.utilities.FormInputValidator
import com.malaabeteam.network.model.LoginResponseDto
import com.malaabeteam.persistance.UserSession
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class SignInViewModel @Inject constructor(
  private val loginRepository: LoginRepository,
  private val session: UserSession,
  private val formInputValidator: FormInputValidator,
  private val errorParser: ErrorParser
): BaseViewModel<SignInUiModel>() {
  fun signIn(
    userId: String,
    password: String,
    type: SignInType,
    email: String?=null
  ){
    viewModelScope.launch {
      try{
        uiState = SignInUiModel(isLoading = true)

        val user: LoginResponseDto = loginRepository.signIn(userId, password)

        if (user.userSession.userProfile.resetPassword == true) {
          uiState = SignInUiModel(
            isLoading = false,
            isLoggedIn = true,
            isResetPassword = true,
            resetPasswordToken = user.userSession.sessionKey
          )
          return@launch
        }

        session.logIn(
          token = user.userSession.sessionKey,
          userId = user.userSession.userProfile.userId,
          isEmailLogin = user.userSession.userProfile.loggedInWithEmail()
        )

        uiState = SignInUiModel(isLoading = false, isLoggedIn = true)
      }catch(t: Throwable){
        onError(t)
      }
    }
  }

  private fun onError(error: Throwable) {
    uiState = SignInUiModel(isLoading = false, errorMessage = errorParser.getErrorMessage(error))
    _errorLiveData.value = R.string.errorGeneral
    Timber.e(error)
  }

  fun validateEmail(email: String) {
    uiState = SignInUiModel(
      emailValid = formInputValidator.emailValid(email)
    )
  }

  fun validatePassword(password: String) {
    uiState = SignInUiModel(
      passwordValid = password.isNotEmpty()
    )
  }
}
