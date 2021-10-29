package com.malaabeteam.malaabeapp.di.module

import android.app.Application
import android.content.Context
import com.malaabeteam.malaabeapp.Config
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import dagger.Module
import dagger.Provides

@Suppress("unused")
@Module
object AppModule {
  @Provides
  fun provideContext(application: Application): Context = application
}
