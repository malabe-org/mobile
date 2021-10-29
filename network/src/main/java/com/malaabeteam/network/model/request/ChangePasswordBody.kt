package com.malaabeteam.network.model.request

data class ChangePasswordBody(
  val oldPassword: String,
  val newPassword: String
)
