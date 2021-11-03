package com.malaabeteam.malaabeapp.di.module

import androidx.lifecycle.ViewModel
import com.malaabeteam.malaabeapp.di.ViewModelKey
import com.malaabeteam.malaabeapp.ui.login.signIn.SignInViewModel
import com.malaabeteam.malaabeapp.ui.login.signUp.SignUpViewModel
import com.malaabeteam.malaabeapp.ui.main.MainViewModel
import com.malaabeteam.malaabeapp.ui.main.browse.BrowseViewModel
import com.malaabeteam.malaabeapp.ui.main.notifications.NotificationViewModel
import com.malaabeteam.malaabeapp.ui.main.profile.ProfileViewModel
import com.malaabeteam.malaabeapp.ui.main.request.DocumentViewModel
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageDescription.RequestDescriptionViewModel
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageDocuments.RequestDocumentsViewModel
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageEnd.RequestEndViewModel
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

  @Binds
  @IntoMap
  @ViewModelKey(BrowseViewModel::class)
  abstract fun bindBrowseViewModel(viewModel: BrowseViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(DocumentViewModel::class)
  abstract fun bindDocumentViewModel(viewModel: DocumentViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(RequestDescriptionViewModel::class)
  abstract fun bindRequestDescriptionViewModel(viewModel: RequestDescriptionViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(RequestDocumentsViewModel::class)
  abstract fun bindRequestDocumentsViewModel(viewModel: RequestDocumentsViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(RequestEndViewModel::class)
  abstract fun bindRequestEndViewModel(viewModel: RequestEndViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(ProfileViewModel::class)
  abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(NotificationViewModel::class)
  abstract fun bindNotificationViewModel(viewModel: NotificationViewModel): ViewModel

}
