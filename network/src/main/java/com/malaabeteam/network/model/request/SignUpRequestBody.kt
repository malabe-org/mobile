package com.malaabeteam.network.model.request

data class SignUpRequestBody(
  val email: String,
  val password: String,
  val firstname: String,
  val lastname: String,
  val phone: String,
  val gender: String,
  val role: String = "seeker"
)

data class SocialSignUpRequestBody(
  val email: String,
  val username: String,
  val password: String,
  val alertOn: Boolean = true,
  val acceptedTerms: Boolean = true,
  val userRole: String = "ROLE_USER",
  val googleUserId: String? = null,
  val facebookUserId: String? = null
)
