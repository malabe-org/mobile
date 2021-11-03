package com.malaabeteam.network

import com.malaabeteam.network.api.*

class MalaabeApi(
    val auth: AuthApi,
    val bankAccount: BankAccountApi,
    val user: UserApi,
    val chat: ChatApi,
    val document: DocumentApi,
    val notification: UserNotificationApi
)
