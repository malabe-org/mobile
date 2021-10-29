package com.malaabeteam.network.model.request

data class SignUpRequestBody(
  val email: String,
  val username: String,
  val password: String,
  val alertOn: Boolean = true,
  val acceptedTerms: Boolean = true,
  val userRole: String = "ROLE_USER"
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
