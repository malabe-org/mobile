package com.malaabeteam.malaabeapp.data.repository

import com.malaabeteam.malaabeapp.di.scope.AppScope
import com.malaabeteam.network.MalaabeApi
import com.malaabeteam.persistance.UserSession
import com.malaabeteam.network.model.request.ChangePasswordBody
import javax.inject.Inject

@AppScope
class LoginRepository @Inject constructor(
  private val api: MalaabeApi,
  private val session: UserSession
) {

  suspend fun signIn(email: String, password: String) =
    api.auth.signIn(email, password)

  suspend fun signInWithGoogle(userId: String, email: String, tokenId: String) =
    api.auth.signInWithGoogle(userId, email, tokenId).response

  suspend fun signInWithFacebook(userId: String, email: String, tokenId: String) =
    api.auth.signInWithFacebook(userId, email, tokenId).response

  suspend fun signUp(email: String, password: String,firstname: String, lastname: String, phone: String, gender: String) =
    api.auth.signUp(email, password, firstname, lastname, phone, gender)

  suspend fun signUpSocial(
    email: String,
    userName: String,
    googleUserId: String? = null,
    facebookUserId: String? = null
  ) =
    api.auth.signUpSocial(email, userName, googleUserId, facebookUserId).response

  suspend fun resetPassword(email: String) = api.auth.resetPassword(email)

  suspend fun changePassword(oldPassword: String, newPassword: String): Boolean {
    session.checkAuthorization()

    return api.auth.changePassword(session.token()!!, ChangePasswordBody(oldPassword, newPassword)).success
  }

  suspend fun resetPassword(token: String, oldPassword: String, newPassword: String): Boolean {
    return api.auth.changePassword(token, ChangePasswordBody(oldPassword, newPassword)).success
  }
}
