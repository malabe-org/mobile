package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.Config.DATE_FORMAT
import com.malaabeteam.malaabeapp.data.model.Account
import com.malaabeteam.malaabeapp.data.model.BankAccount
import com.malaabeteam.network.model.AccountDto
import com.malaabeteam.network.model.BankAccountDto
import org.threeten.bp.format.DateTimeFormatter
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale.ENGLISH
import javax.inject.Inject

class BankAccountMapper @Inject constructor() {

  private val roundingMode = RoundingMode.HALF_EVEN
  private val dateFormatter by lazy { DateTimeFormatter.ofPattern(DATE_FORMAT, ENGLISH) }

  fun fromNetwork(item: AccountDto) = Account(
    id = item.id,
    accountBalance = item.accountBalance?.toBigDecimal()?.setScale(2, roundingMode) ?: BigDecimal.ZERO,
    amountPending = item.amountPending?.toBigDecimal()?.setScale(2, roundingMode) ?: BigDecimal.ZERO,
    transferableAmount = (item.accountBalance?.minus(item.amountPending ?: 0.0)
      ?.coerceAtLeast(0.0))
      ?.toBigDecimal()?.setScale(2, roundingMode) ?: BigDecimal.ZERO
  )

  fun fromNetwork(item: BankAccountDto) = BankAccount(
    id = item.id,
    accountNumber = item.accountNumber
  )

//  fun fromNetwork(item: TransactionDto) = Transaction(
//    id = item.id,
//    amount = item.amount?.toBigDecimal() ?: BigDecimal.ZERO,
//    isDebit = item.isDebit,
//    dateCreated = LocalDateTime.parse(item.dateCreated, dateFormatter),
//    isPending = item.isPending,
//    description = item.description,
//    order = item.orderProduct?.let {
//      Order(
//        id = it.orderInfo.id,
//        orderDate = LocalDateTime.parse(it.orderInfo.orderDate, dateFormatter),
//        totalAmount = it.orderInfo.totalAmount.toBigDecimal(),
//        totalItems = it.orderInfo.totalItems
//      )
//    }
//  )
}
