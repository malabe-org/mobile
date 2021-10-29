package com.malaabeteam.network.model

data class AddressDto(
  val id: Long,
  val addrType: String,
  val address1: String,
  val address2: String?,
  val firstName: String,
  val lastName: String,
  val city: String,
  val state: String?,
  val zipCode: String?
)
