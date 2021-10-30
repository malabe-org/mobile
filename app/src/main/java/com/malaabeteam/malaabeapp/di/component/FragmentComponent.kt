package com.malaabeteam.malaabeapp.di.component

import com.malaabeteam.malaabeapp.ui.login.signIn.SignInFragment
import com.malaabeteam.malaabeapp.ui.login.signUp.SignUpFragment
import com.malaabeteam.malaabeapp.ui.main.browse.BrowseFragment
import dagger.Subcomponent

@Subcomponent
interface FragmentComponent {
  @Subcomponent.Factory
  interface Factory {
    fun create(): FragmentComponent
  }

  fun inject(fragment: SignInFragment)
  fun inject(fragment: SignUpFragment)
  fun inject(fragment: BrowseFragment)
}
