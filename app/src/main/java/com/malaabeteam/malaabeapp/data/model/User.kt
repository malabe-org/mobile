package com.malaabeteam.malaabeapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
  val id: Long,
  val firstName: String,
  val lastName: String,
  val imageUrl: String?,
  val email: String,
  val username: String,
  val gender: Gender,
  val role: String,
  val isActive: Boolean,
  val mobile: String,
  val alertsOn: Boolean,
  val address: UserAddress?,
  val rating: Double,
  val addresses: List<UserAddress>,
  val resetPassword: Boolean,
  val password: String
) : Parcelable {

  companion object {
    val UNKNOWN = User(
      -1,
      "",
      "",
      null,
      "",
      "",
      Gender.OTHER,
      "",
      mobile = "",
      isActive = false,
      alertsOn = false,
      address = null,
      rating = 0.0,
      addresses = emptyList(),
      resetPassword = false,
      password = ""
    )
  }
}
