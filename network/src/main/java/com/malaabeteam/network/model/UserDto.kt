package com.malaabeteam.network.model

data class UserDto(
  val _id: String,
  val firstname: String?,
  val lastname: String?,
  val email: String?,
  val cni: String?,
  val phone: String?,
  val role: String?,
  val gender: String?
) {

}
