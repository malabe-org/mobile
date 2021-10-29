package com.malaabeteam.network.service

import com.malaabeteam.network.model.response.ItemResponse
import com.malaabeteam.network.model.LoginResponseDto
import com.malaabeteam.network.model.request.ChangePasswordBody
import com.malaabeteam.network.model.request.SignInRequestBody
import com.malaabeteam.network.model.request.SignInSocialRequestBody
import com.malaabeteam.network.model.request.SignUpRequestBody
import com.malaabeteam.network.model.request.SocialSignUpRequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthService {

  @POST("auth")
  suspend fun signIn(@Body body: SignInRequestBody): ItemResponse<LoginResponseDto>

  @POST("auth/google")
  suspend fun signInWithGoogle(@Body body: SignInSocialRequestBody): ItemResponse<LoginResponseDto>

  @POST("auth/facebook")
  suspend fun signInWithFacebook(@Body body: SignInSocialRequestBody): ItemResponse<LoginResponseDto>

  @POST("user")
  suspend fun signUp(@Body body: SignUpRequestBody): ItemResponse<LoginResponseDto>

  @POST("user")
  suspend fun socialSignUp(@Body body: SocialSignUpRequestBody): ItemResponse<LoginResponseDto>

  @PUT("auth")
  suspend fun changePassword(
    @Query("session") session: String,
    @Body changePasswordBody: ChangePasswordBody
  ): ItemResponse<LoginResponseDto>

  @PUT("auth/reset")
  suspend fun resetPassword(@Query("email") email: String)
}
