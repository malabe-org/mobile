package com.malaabeteam.network.api

import com.malaabeteam.network.model.AccountDto
import com.malaabeteam.network.model.BankAccountDto
import com.malaabeteam.network.model.TransactionDto
import com.malaabeteam.network.model.request.AddAccountBody
import com.malaabeteam.network.service.BankAccountService
import java.math.BigDecimal
import javax.inject.Inject

class BankAccountApi @Inject constructor(
    private val service: BankAccountService
) {

    suspend fun getAccount(
        session: String
    ): AccountDto = service.getAccount(session).paylod

    suspend fun addAccount(
        session: String,
        accountNumber: String,
        routingNumber: String,
        bankName: String
    ): AccountDto = service.addAccount(session, AddAccountBody(accountNumber, routingNumber, bankName)).paylod

    suspend fun transferBalance(
        session: String,
        amount: BigDecimal,
        bankId: Long
    ) = service.transferBalance(session, amount.toDouble(), bankId).paylod

    suspend fun getBankAccounts(
        session: String
    ): List<BankAccountDto>? = service.getBankAccounts(session).paylod

    suspend fun getTransactions(
        session: String,
        first: Int,
        pageSize: Int
    ): List<TransactionDto> = service.getTransactions(session, first, pageSize).paylod ?: emptyList()
}
