package com.malaabeteam.malaabeapp.ui.common

import androidx.appcompat.app.AppCompatActivity
import com.malaabeteam.malaabeapp.di.DaggerViewModelFactory
import javax.inject.Inject

abstract class BaseActivity<T: BaseViewModel<out UiModel>>():
AppCompatActivity(){
  @Inject
  lateinit var viewModelFactory: DaggerViewModelFactory
  protected abstract val viewModel: T
}
