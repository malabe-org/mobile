package com.malaabeteam.malaabeapp.utilities

import javax.inject.Inject

class FormInputValidator @Inject constructor() {

  // Min 6 chars, at least one upper/lower case letter and at least one digit.
  private val passwordRegexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$".toRegex()

  // Min 1 char
  private val userNameRegexp = "^.+$".toRegex()
  private val nameRegex = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+\$".toRegex()

  fun passwordValid(password: String) = passwordRegexp.matches(password)
  fun userNameValid(userName: String) = nameRegex.matches(userName)
  fun emailValid(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
