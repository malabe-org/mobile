package com.malaabeteam.network.model

data class TransactionOrderInfoDto(
  val id: Long,
  val orderDate: String,
  val totalItems: Int,
  val totalAmount: Double
)

data class TransactionOrderDto(
  val orderInfo: TransactionOrderInfoDto
)

data class TransactionDto(
  val id: Long,
  val amount: Double?,
  val dateCreated: String,
  val isPending: Boolean,
  val isDebit: Boolean,
  val description: String?,
  val orderProduct: TransactionOrderDto?
)
