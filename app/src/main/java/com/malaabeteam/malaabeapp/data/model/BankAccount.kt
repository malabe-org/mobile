package com.malaabeteam.malaabeapp.data.model

data class BankAccount(
  val id: Long,
  val accountNumber: String,
  var isChecked: Boolean = false
)
