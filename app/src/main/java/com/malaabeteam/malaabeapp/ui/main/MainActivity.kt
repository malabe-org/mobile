package com.malaabeteam.malaabeapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.appComponent
import com.malaabeteam.malaabeapp.di.component.FragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseActivity
import com.malaabeteam.malaabeapp.ui.login.LoginActivity
import com.malaabeteam.persistance.UserSession
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

  lateinit var fragmentComponent: FragmentComponent

  override val viewModel by viewModels<MainViewModel> { viewModelFactory }

  @Inject
  lateinit var session: UserSession

  override fun onCreate(savedInstanceState: Bundle?) {
    appComponent().inject(this)
    fragmentComponent = appComponent().fragmentComponent().create()
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    //openLoginActivity()
  }


  private fun openLoginActivity() {
    startActivity(Intent(this, LoginActivity::class.java).apply {
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    })
  }
}
