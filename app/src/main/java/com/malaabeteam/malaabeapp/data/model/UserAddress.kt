package com.malaabeteam.malaabeapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class AddrType(val addrValue: String) {
  DEFAULT("DEFAULT"),
  HOME("HOME"),
}

@Parcelize
data class UserAddress(
  val id: Long,
  val addrType: String,
  val address1: String,
  val address2: String?,
  val firstName: String,
  val lastName: String,
  val city: String?,
  val state: String?,
  val zipCode: String?
) : Parcelable
