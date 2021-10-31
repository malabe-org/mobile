package com.malaabeteam.network.model

data class UserDto(
  val userId: String,
  val firstname: String?,
  val lastname: String?,
  val email: String?,
  val cni: String?,
  val phone: String?,
  val userRole: String?,
  val isDeleted: Boolean?,
  val gender: String?,
  val localisation: String?,
  val address: AddressDto?,
  val addresses: List<AddressDto>?,
  val googleUserId: String?,
  val facebookUserId: String?,
  val resetPassword: Boolean?,
  val password: String?
) {

  fun loggedInWithEmail(): Boolean = password != null && password.isNotBlank()
}
