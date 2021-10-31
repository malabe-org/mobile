package com.malaabeteam.malaabeapp.ui.login.signUp

import androidx.lifecycle.viewModelScope
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.repository.LoginRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.utilities.ErrorParser
import com.malaabeteam.malaabeapp.utilities.FormInputValidator
import com.malaabeteam.persistance.UserSession
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
  private val loginRepository: LoginRepository,
  private val session: UserSession,
  private val formInputValidator: FormInputValidator,
  private val errorParser: ErrorParser
) : BaseViewModel<SignUpUiModel>() {
  fun signUp(email: String, userName: String, password: String) {
    viewModelScope.launch {
      try {
        uiState = SignUpUiModel(isLoading = true)


        val user = loginRepository.signUp(
          email = email,
          userName = userName,
          password = password
        )

        session.logIn(
          token = user.token?:"",
          userId = user.userId?:"",
          isEmailLogin = true
        )

        uiState = SignUpUiModel(isLoading = false, isLoggedIn = true)
      } catch (t: Throwable) {
        onError(t)
      }
    }
  }

  private fun onError(error: Throwable) {
    uiState = SignUpUiModel(isLoading = false, errorMessage = errorParser.getErrorMessage(error))
    _errorLiveData.value = R.string.errorGeneral
    Timber.e(error)
  }

  fun validateEmail(email: String) {
    uiState = SignUpUiModel(
      emailValid = formInputValidator.emailValid(email)
    )
  }

  fun validatePassword(password: String) {
    uiState = SignUpUiModel(
      passwordValid = formInputValidator.passwordValid(password)
    )
  }

  fun validateUserName(userName: String) {
    uiState = SignUpUiModel(
      userNameValid = formInputValidator.userNameValid(userName)
    )
  }

  fun checkTerms(checked: Boolean) {
    uiState = SignUpUiModel(
      termsAccepted = checked
    )
  }
}
