package com.malaabeteam.malaabeapp

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import com.facebook.stetho.Stetho
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import com.malaabeteam.malaabeapp.di.component.AppComponent
import com.malaabeteam.malaabeapp.di.component.DaggerAppComponent
import com.malaabeteam.malaabeapp.ui.login.LoginActivity
import com.malaabeteam.malaabeapp.ui.main.MainActivity
import com.stripe.android.PaymentConfiguration
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

  lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
      Stetho.initializeWithDefaults(this)
    }
    FirebaseApp.initializeApp(this)
    AndroidThreeTen.init(this)
    BigImageViewer.initialize(GlideImageLoader.with(applicationContext))
   // PaymentConfiguration.init(this, Config.STRIPE_KEY)

    setupComponents()

  }

  private fun setupComponents() {
    appComponent = DaggerAppComponent.builder()
      .application(this)
      .build()

    appComponent.inject(this)
  }
}

fun Activity.appComponent() = (application as App).appComponent

fun Service.appComponent() = (application as App).appComponent

fun Fragment.fragmentComponent() = (requireActivity() as MainActivity).fragmentComponent

fun Fragment.loginFragmentComponent() = (requireActivity() as LoginActivity).fragmentComponent
