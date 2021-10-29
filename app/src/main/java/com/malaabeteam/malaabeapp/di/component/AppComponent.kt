package com.malaabeteam.malaabeapp.di.component

import android.app.Application
import com.malaabeteam.malaabeapp.App
import com.malaabeteam.malaabeapp.di.module.SubcomponentsModule
import com.malaabeteam.malaabeapp.di.module.ViewModelsModule
import com.malaabeteam.malaabeapp.di.module.AppModule
import com.malaabeteam.malaabeapp.di.scope.AppScope
import com.malaabeteam.malaabeapp.ui.login.LoginActivity
import com.malaabeteam.malaabeapp.ui.main.MainActivity
import com.malaabeteam.network.di.ApiModule
import com.malaabeteam.network.di.RetrofitModule
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
  modules = [
    ViewModelsModule::class,
    SubcomponentsModule::class,
    RetrofitModule::class,
    ApiModule::class,
    AppModule::class
  ]
)
interface AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun fragmentComponent(): FragmentComponent.Factory

  fun inject(app: App)
  fun inject(activity: MainActivity)
  fun inject(activity: LoginActivity)
}
