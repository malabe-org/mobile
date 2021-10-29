package com.malaabeteam.malaabeapp.di.module

import androidx.lifecycle.ViewModel
import com.malaabeteam.malaabeapp.di.ViewModelKey
import com.malaabeteam.malaabeapp.ui.login.signIn.SignInViewModel
import com.malaabeteam.malaabeapp.ui.login.signUp.SignUpViewModel
import com.malaabeteam.malaabeapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelsModule {
  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(SignInViewModel::class)
  abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(SignUpViewModel::class)
  abstract fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

}
