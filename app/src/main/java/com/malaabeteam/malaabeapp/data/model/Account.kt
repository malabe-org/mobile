package com.malaabeteam.malaabeapp.data.model

import java.math.BigDecimal

data class Account(
  val id: Long,
  val accountBalance: BigDecimal,
  val amountPending: BigDecimal,
  val transferableAmount: BigDecimal
)
