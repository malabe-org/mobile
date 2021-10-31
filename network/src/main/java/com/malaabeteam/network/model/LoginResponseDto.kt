package com.malaabeteam.network.model

data class LoginResponseDto(
  var userId: String? = null,
  var firstname: String? = null,
  var lastname: String? = null,
  var role: String? = null,
  var token: String? = null,
  var isFirstConnection: Boolean = false
)

