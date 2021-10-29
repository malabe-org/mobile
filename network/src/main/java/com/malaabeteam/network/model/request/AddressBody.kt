package com.malaabeteam.network.model.request

data class AddressBody(
  val addrType: String,
  val address1: String,
  val address2: String?,
  val firstName: String,
  val lastName: String,
  val city: String,
  val state: String,
  val zipCode: String,
  val id: Long = -1
)
