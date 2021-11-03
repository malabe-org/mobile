package com.malaabeteam.network.di

import com.malaabeteam.network.MalaabeApi
import com.malaabeteam.network.api.*
import com.malaabeteam.network.service.*
import com.teamxing.network.service.UserService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object ApiModule {


    @Provides
    fun providesApi(retrofit: Retrofit): MalaabeApi =
      MalaabeApi(
        auth = AuthApi(retrofit.create(AuthService::class.java)),
        user = UserApi(retrofit.create(UserService::class.java)),
        bankAccount = BankAccountApi(retrofit.create(BankAccountService::class.java)),
        chat = ChatApi(retrofit.create(ChatService::class.java)),
        document = DocumentApi(retrofit.create(DocumentService::class.java)),
        notification = UserNotificationApi(retrofit.create(UserNotificationService::class.java))
    )
}
