package com.malaabeteam.malaabeapp.ui.login.signIn

import com.malaabeteam.malaabeapp.ui.common.UiModel

data class SignInUiModel(
  val isLoading: Boolean? = null,
  val isLoggedIn: Boolean? = null,
  val passwordValid: Boolean? = null,
  val emailValid: Boolean? = null,
  val errorMessage: String? = null,
  val isResetPassword: Boolean? = null,
  val resetPasswordToken: String? = null
): UiModel() {
  override fun update(newModel: UiModel)=(newModel as SignInUiModel).copy(
    isLoading = newModel.isLoading ?: isLoading,
    isLoggedIn = newModel.isLoggedIn ?: isLoggedIn,
    passwordValid = newModel.passwordValid ?: passwordValid,
    emailValid = newModel.emailValid ?: emailValid,
    isResetPassword = newModel.isResetPassword ?: isResetPassword,
    resetPasswordToken = newModel.resetPasswordToken ?: resetPasswordToken
  )
}
