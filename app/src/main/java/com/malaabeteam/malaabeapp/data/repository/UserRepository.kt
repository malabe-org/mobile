package com.malaabeteam.malaabeapp.data.repository

import com.malaabeteam.malaabeapp.data.mappers.Mappers
import com.malaabeteam.malaabeapp.data.model.Gender
import com.malaabeteam.malaabeapp.data.model.User
import com.malaabeteam.malaabeapp.data.model.UserAddress
import com.malaabeteam.malaabeapp.di.scope.AppScope
import com.malaabeteam.network.MalaabeApi
import com.malaabeteam.network.model.request.AddressBody
import com.malaabeteam.network.model.request.ChangeEmailBody
import com.malaabeteam.network.model.request.UpdateUserBody
import com.malaabeteam.persistance.UserSession
import okhttp3.MultipartBody
import javax.inject.Inject

@AppScope
class UserRepository @Inject constructor(
  private val api: MalaabeApi,
  private val session: UserSession,
  private val mappers: Mappers
) {

  suspend fun getUser(): User {
    session.checkAuthorization()
    return mappers.user.fromNetwork(api.user.fetchUser(session.token()!!))
  }

  suspend fun addUserAddress(address: AddressBody): UserAddress {
    session.checkAuthorization()
    return mappers.user.mapUserAddress(api.user.addAddress(session.token()!!, address))
  }

  suspend fun deleteAddress(id: Long): Boolean {
    session.checkAuthorization()
    return api.user.deleteAddress(session.token()!!, id)
  }

  suspend fun changeEmail(email: String): Boolean {
    session.checkAuthorization()
    return api.user.changeEmail(session.token()!!, ChangeEmailBody(email)).success
  }

  suspend fun changePicture(file: MultipartBody.Part): Boolean {
    session.checkAuthorization()

    val sessionPart = MultipartBody.Part.createFormData("session", session.token()!!)

    return api.user.changePicture(sessionPart, file).success
  }

  suspend fun updateUser(
    firstName: String,
    lastName: String,
    mobile: String,
    gender: Gender
  ): Boolean {
    session.checkAuthorization()
    return api.user.updateUser(session.token()!!, UpdateUserBody(firstName, lastName, mobile, gender.apiName)).success
  }

  suspend fun updateDeviceId(deviceId: String) {
    val token = session.checkAuthorization()
    api.user.updateDeviceId(token, deviceId)
  }
}
