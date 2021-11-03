package com.malaabeteam.malaabeapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
  val _id: String,
  val firstname: String,
  val lastname: String,
  val email: String,
  val phone: String,
  val gender: String,
  val role: String,
  val cni: String
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
      cni = ""
    )
  }
}
