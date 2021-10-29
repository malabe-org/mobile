package com.malaabeteam.malaabeapp.ui.login.signUp

import com.malaabeteam.malaabeapp.ui.common.UiModel

data class SignUpUiModel(
  val isLoading: Boolean? = null,
  val isLoggedIn: Boolean? = null,
  val passwordValid: Boolean? = null,
  val emailValid: Boolean? = null,
  val userNameValid: Boolean? = null,
  val termsAccepted: Boolean? = null,
  val errorMessage: String? = null
): UiModel() {
  override fun update(newModel: UiModel) =
    (newModel as SignUpUiModel).copy(
      isLoading = newModel.isLoading ?: isLoading,
      isLoggedIn = newModel.isLoggedIn ?: isLoggedIn,
      passwordValid = newModel.passwordValid ?: passwordValid,
      emailValid = newModel.emailValid ?: emailValid,
      userNameValid = newModel.userNameValid ?: userNameValid,
      termsAccepted = newModel.termsAccepted ?: termsAccepted
    )
}
