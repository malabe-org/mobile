package com.malaabeteam.network.api

import com.malaabeteam.network.model.request.AddressBody
import com.malaabeteam.network.model.request.ChangeEmailBody
import com.malaabeteam.network.model.request.UpdateUserBody
import com.teamxing.network.service.UserService
import okhttp3.MultipartBody
import javax.inject.Inject

class UserApi @Inject constructor(private val service: UserService) {

    suspend fun fetchUser(token: String) = service.fetchUser(token).response

    suspend fun addAddress(token: String, address: AddressBody) = service.addAddress(token, address).response

    suspend fun deleteAddress(token: String, addressId: Long) = service.deleteAddress(addressId, token).success

    suspend fun changeEmail(session: String, body: ChangeEmailBody) = service.changeEmail(session, body)

    suspend fun updateUser(session: String, body: UpdateUserBody) = service.updateUser(session, body)

    suspend fun updateDeviceId(session: String, deviceId: String) = service.updateDeviceId(session, deviceId)

    suspend fun changePicture(session: MultipartBody.Part, file: MultipartBody.Part) = service.changePicture(session, file)
}
