package com.malaabeteam.network.model

data class AccountDto(
  val id: Long,
  val accountBalance: Double?,
  val amountPending: Double?
)
