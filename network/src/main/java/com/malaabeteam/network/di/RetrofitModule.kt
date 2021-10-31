package com.malaabeteam.network.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.malaabeteam.network.BuildConfig
import com.malaabeteam.network.Config.MALAABE_API_BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

@Module
object RetrofitModule {

  private const val TIMEOUT_DURATION = 30L

  @Provides
  fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
    Retrofit.Builder()
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create())
      .baseUrl(MALAABE_API_BASE_URL)
      .build()

  @Provides
  fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

  @Provides
  fun providesOkHttp(
    httpLoggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient =
    OkHttpClient.Builder()
      .writeTimeout(TIMEOUT_DURATION, SECONDS)
      .readTimeout(TIMEOUT_DURATION, SECONDS)
      .callTimeout(TIMEOUT_DURATION, SECONDS)
      .addNetworkInterceptor(StethoInterceptor())
      .addInterceptor(httpLoggingInterceptor)
      .build()

  @Provides
  fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
      level = when {
        BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
      }
    }
}
