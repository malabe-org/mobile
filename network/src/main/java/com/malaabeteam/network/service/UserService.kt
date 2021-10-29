package com.teamxing.network.service

import com.malaabeteam.network.model.AddressDto
import com.malaabeteam.network.model.UserDto
import com.malaabeteam.network.model.request.AddressBody
import com.malaabeteam.network.model.request.ChangeEmailBody
import com.malaabeteam.network.model.request.UpdateUserBody
import com.malaabeteam.network.model.response.ItemResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

  @GET("user")
  suspend fun fetchUser(@Query("session") sessionKey: String): ItemResponse<UserDto>

  @PUT("user/address")
  suspend fun addAddress(
    @Query("session") sessionKey: String,
    @Body address: AddressBody
  ): ItemResponse<AddressDto>

  @DELETE("user/address/{id}")
  suspend fun deleteAddress(
    @Path("id") addressId: Long,
    @Query("session") sessionKey: String
  ): ItemResponse<Any>

  @PUT("user")
  suspend fun changeEmail(
    @Query("session") session: String,
    @Body body: ChangeEmailBody
  ): ItemResponse<Any>

  @PUT("user")
  suspend fun updateUser(
    @Query("session") session: String,
    @Body body: UpdateUserBody
  ): ItemResponse<Any>

  @PUT("user/device")
  suspend fun updateDeviceId(
    @Query("session") session: String,
    @Query("deviceId") deviceId: String
  ): ItemResponse<Any>

  @Multipart
  @POST("user/upload")
  suspend fun changePicture(
    @Part session: MultipartBody.Part,
    @Part file: MultipartBody.Part
  ): ItemResponse<Any>
}
