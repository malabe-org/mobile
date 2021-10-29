package com.malaabeteam.malaabeapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.appComponent
import com.malaabeteam.malaabeapp.di.component.FragmentComponent
import com.malaabeteam.malaabeapp.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
  lateinit var fragmentComponent: FragmentComponent
    override fun onCreate(savedInstanceState: Bundle?) {
      appComponent().inject(this)
      fragmentComponent = appComponent().fragmentComponent().create()
      super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

  fun openMainActivity() {
    startActivity(Intent(this, MainActivity::class.java).apply {
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    })
  }
}
