package com.malaabeteam.network.model.request

data class AddAccountBody(
  val accountNumber: String,
  val routingNumber: String,
  val bankName: String,
  val accountType: String = "DEFAULT"
)
