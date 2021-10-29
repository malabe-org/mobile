package com.malaabeteam.network.api

import com.malaabeteam.network.model.request.*
import com.malaabeteam.network.service.AuthService
import java.util.*
import javax.inject.Inject

class AuthApi  @Inject constructor(
    private val service: AuthService
) {

    suspend fun signIn(email: String, password: String) =
        service.signIn(SignInRequestBody(email, password))

    suspend fun signInWithGoogle(userId: String, email: String, tokenId: String) =
        service.signInWithGoogle(SignInSocialRequestBody(userId, email, tokenId))

    suspend fun signInWithFacebook(userId: String, email: String, tokenId: String) =
        service.signInWithFacebook(SignInSocialRequestBody(userId, email, tokenId))

    suspend fun signUp(email: String, userName: String, password: String) =
        service.signUp(SignUpRequestBody(email, userName, password))

    suspend fun signUpSocial(
        email: String,
        userName: String,
        googleUserId: String? = null,
        facebookUserId: String? = null
    ) = service.socialSignUp(
        SocialSignUpRequestBody(
            email,
            userName,
            UUID.randomUUID().toString().replace("-", "").take(19).plus("X"),
            googleUserId = googleUserId,
            facebookUserId = facebookUserId
        )
    )

    suspend fun resetPassword(email: String) = service.resetPassword(email)

    suspend fun changePassword(session: String, body: ChangePasswordBody) = service.changePassword(session, body)
}