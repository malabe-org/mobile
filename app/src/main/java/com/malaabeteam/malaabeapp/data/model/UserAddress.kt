package com.malaabeteam.malaabeapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class AddrType(val addrValue: String) {
  DEFAULT("DEFAULT"),
  HOME("HOME"),
}

@Parcelize
data class UserAddress(
  val region: String,
  val department: String,
  val city: String?
) : Parcelable
