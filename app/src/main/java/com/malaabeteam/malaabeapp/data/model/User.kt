package com.malaabeteam.malaabeapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
  val userId: String,
  val firstname: String,
  val lastname: String,
  val email: String,
  val phone: String,
  val gender: String,
  val role: String,
  val isDeleted: Boolean,
  val cni: String,
  val address: UserAddress?,
  val addresses: List<UserAddress>?,
  val localisation: String,
  val password: String
) : Parcelable {

  companion object {
    val UNKNOWN = User(
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      isDeleted = false,
      cni = "",
      address = null,
      addresses = emptyList(),
      localisation = "",
      password = ""
    )
  }
}
