package com.malaabeteam.persistance

import android.content.Context
import android.util.Log
import com.malaabeteam.persistance.exception.NotAuthorizedException
import javax.inject.Inject

class UserSession @Inject constructor(context: Context) : BaseStorage(context) {

  companion object {
    private const val KEY_TOKEN = "KEY_TOKEN"
    private const val KEY_USER_ID = "KEY_USER_ID"
    private const val KEY_SOCIAL_LOGIN = "KEY_SOCIAL_LOGIN"
    private const val KEY_EMAIL_LOGIN = "KEY_EMAIL_LOGIN"
  }

  override val storageName = "UserSession"

  fun checkAuthorization(): String {
    if (!isAuthorized()) throw NotAuthorizedException()
    return token()!!
  }

  fun logIn(
    token: String,
    userId: String,
    isEmailLogin: Boolean
  ) {
    Log.d("LogInUser","token: $token")
    putString(KEY_TOKEN, token)
    putString(KEY_USER_ID, userId)
    putBoolean(KEY_EMAIL_LOGIN, isEmailLogin)
  }

  fun token() = getString(KEY_TOKEN)

  fun id() = getString(KEY_USER_ID)

  fun logOut() = removeKeys(KEY_TOKEN, KEY_USER_ID, KEY_EMAIL_LOGIN)

  fun isAuthorized() = getString(KEY_TOKEN) != null

  fun isEmailLogin() = getBoolean(KEY_EMAIL_LOGIN)
}
