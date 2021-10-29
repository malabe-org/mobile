package com.malaabeteam.network.model

data class UserDto(
  val userId: Long,
  val firstName: String?,
  val lastName: String?,
  val imageUrl: String?,
  val email: String?,
  val username: String?,
  val userRole: String?,
  val isActive: Boolean?,
  val alertsOn: Boolean?,
  val mobile: String?,
  val gender: String?,
  val address: AddressDto?,
  val addresses: List<AddressDto>?,
  val googleUserId: String?,
  val facebookUserId: String?,
  val resetPassword: Boolean?,
  val password: String?
) {

  fun loggedInWithEmail(): Boolean = password != null && password.isNotBlank()
}
