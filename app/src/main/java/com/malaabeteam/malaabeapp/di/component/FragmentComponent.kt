package com.malaabeteam.malaabeapp.di.component

import com.malaabeteam.malaabeapp.ui.login.signIn.SignInFragment
import com.malaabeteam.malaabeapp.ui.login.signUp.SignUpFragment
import com.malaabeteam.malaabeapp.ui.main.browse.BrowseFragment
import com.malaabeteam.malaabeapp.ui.main.notifications.NotificationFragment
import com.malaabeteam.malaabeapp.ui.main.profile.ProfileFragment
import com.malaabeteam.malaabeapp.ui.main.request.DocumentFragmentSheet
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageDescription.RequestDescriptionFragment
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageDocuments.RequestDocumentsFragment
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageEnd.RequestEndFragment
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
  fun inject(fragment: DocumentFragmentSheet)
  fun inject(fragment: RequestDescriptionFragment)
  fun inject(fragment: RequestDocumentsFragment)
  fun inject(fragment: RequestEndFragment)
  fun inject(fragment: ProfileFragment)
  fun inject(fragment: NotificationFragment)
}
