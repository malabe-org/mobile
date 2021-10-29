package com.malaabeteam.network.service

import com.malaabeteam.network.model.AccountDto
import com.malaabeteam.network.model.BankAccountDto
import com.malaabeteam.network.model.TransactionDto
import com.malaabeteam.network.model.request.AddAccountBody
import com.malaabeteam.network.model.response.ItemResponse
import com.malaabeteam.network.model.response.ListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BankAccountService {

  @GET("account")
  suspend fun getAccount(
    @Query("session") session: String
  ): ItemResponse<AccountDto>

  @POST("account")
  suspend fun addAccount(
    @Query("session") session: String,
    @Body accountBody: AddAccountBody
  ): ItemResponse<AccountDto>

  @PUT("account")
  suspend fun transferBalance(
    @Query("session") session: String,
    @Query("amount") amount: Double,
    @Query("bankId") bankId: Long
  ): ItemResponse<Any>

  @GET("account/bank")
  suspend fun getBankAccounts(
    @Query("session") session: String
  ): ListResponse<BankAccountDto>

  @GET("account/ledger")
  suspend fun getTransactions(
    @Query("session") session: String,
    @Query("first") first: Int,
    @Query("pageSize") pageSize: Int
  ): ListResponse<TransactionDto>
}
