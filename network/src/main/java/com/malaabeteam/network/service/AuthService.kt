package com.malaabeteam.network.service

import com.malaabeteam.network.model.response.ItemResponse
import com.malaabeteam.network.model.LoginResponseDto
import com.malaabeteam.network.model.request.ChangePasswordBody
import com.malaabeteam.network.model.request.SignInRequestBody
import com.malaabeteam.network.model.request.SignInSocialRequestBody
import com.malaabeteam.network.model.request.SignUpRequestBody
import com.malaabeteam.network.model.request.SocialSignUpRequestBody
import retrofit2.http.*

interface AuthService {

  @POST(" /api/users/login")
  suspend fun signIn(@Body body: SignInRequestBody): LoginResponseDto

  @POST("auth/google")
  suspend fun signInWithGoogle(@Body body: SignInSocialRequestBody): ItemResponse<LoginResponseDto>

  @POST("auth/facebook")
  suspend fun signInWithFacebook(@Body body: SignInSocialRequestBody): ItemResponse<LoginResponseDto>

  @POST(" /api/users/signup")
  suspend fun signUp(@Body body: SignUpRequestBody): LoginResponseDto

  @POST("user")
  suspend fun socialSignUp(@Body body: SocialSignUpRequestBody): ItemResponse<LoginResponseDto>

  @PUT("auth")
  suspend fun changePassword(
    @Query("session") session: String,
    @Body changePasswordBody: ChangePasswordBody
  ): ItemResponse<LoginResponseDto>

  @PUT("auth/reset")
  suspend fun resetPassword(@Query("email") email: String)

  @POST("api/users/logout")
  suspend fun logout(@Header("Authorization") token: String)
}
