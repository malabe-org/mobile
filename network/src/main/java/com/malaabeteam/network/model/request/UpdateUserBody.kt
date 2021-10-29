package com.malaabeteam.network.model.request

data class UpdateUserBody(
  val firstName: String,
  val lastName: String,
  val mobile: String,
  val gender: String
)
