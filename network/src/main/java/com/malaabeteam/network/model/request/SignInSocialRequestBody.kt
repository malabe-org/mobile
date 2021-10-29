package com.malaabeteam.network.model.request

data class SignInSocialRequestBody(
  val userId: String,
  val email: String,
  val token: String
)
