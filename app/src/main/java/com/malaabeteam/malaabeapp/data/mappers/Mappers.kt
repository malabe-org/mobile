package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.di.scope.AppScope
import javax.inject.Inject

@AppScope
class Mappers @Inject constructor(
  val document: DocumentMapper,
  val user: UserMapper,
  val chat: ChatMapper,
  val bankAccountMapper: BankAccountMapper,
  val notification: NotificationMapper
)
