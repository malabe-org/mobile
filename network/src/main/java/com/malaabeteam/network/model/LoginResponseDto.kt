package com.malaabeteam.network.model

data class LoginResponseDto(val userSession: UserSessionDto)

data class UserSessionDto(val sessionKey: String, val userProfile: UserDto)
