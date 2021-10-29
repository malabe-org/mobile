package com.malaabeteam.malaabeapp.ui.common

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.di.DaggerViewModelFactory
import com.malaabeteam.malaabeapp.ui.main.MainActivity
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel<out UiModel>>(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

  @Inject
  lateinit var viewModelFactory: DaggerViewModelFactory
  protected abstract val viewModel: T

  protected var isInitialized = false
  protected open val showMenu = true
  protected open val ignoreMenu = false

  //private fun showNavigation() = (activity as? MainActivity)?.showNavigation()

  //private fun hideNavigation() = (activity as? MainActivity)?.hideNavigation()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    setupStatusBar()
//    if (!ignoreMenu) {
//      //if (showMenu) showNavigation() else hideNavigation()
//    }
  }

  private fun setupStatusBar() {
    ViewCompat.setOnApplyWindowInsetsListener(requireView()) { view, insets ->
      val updated = WindowInsetsCompat.Builder()
        .setSystemWindowInsets(Insets.of(0, insets.systemWindowInsetTop, 0, insets.systemWindowInsetBottom))
        .build()
      ViewCompat.onApplyWindowInsets(view, updated)
    }
  }

  fun handleLightToolbarBack() {
    //requireView().findViewById<ImageView>(R.id.lightToolbarBack).onClick { requireActivity().onBackPressed() }
  }

  fun handleToolbarBack() {
   // requireView().findViewById<ImageView>(R.id.toolbarBack).onClick { requireActivity().onBackPressed() }
  }

}
