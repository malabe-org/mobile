package com.malaabeteam.malaabeapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.malaabeteam.common_ui.extensions.showInfoDialog
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.appComponent
import com.malaabeteam.malaabeapp.di.component.FragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseActivity
import com.malaabeteam.malaabeapp.ui.common.OnTabReselectedListener
import com.malaabeteam.malaabeapp.ui.login.LoginActivity
import com.malaabeteam.malaabeapp.utilities.PresentationShownState
import com.malaabeteam.malaabeapp.utilities.extensions.dimenToPx
import com.malaabeteam.persistance.UserSession
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

  lateinit var fragmentComponent: FragmentComponent

  override val viewModel by viewModels<MainViewModel> { viewModelFactory }

  @Inject
  lateinit var session: UserSession

  @Inject
  lateinit var presentationShownState: PresentationShownState

  override fun onCreate(savedInstanceState: Bundle?) {
    appComponent().inject(this)
    fragmentComponent = appComponent().fragmentComponent().create()
    super.onCreate(savedInstanceState)

    if(!presentationShownState.wasShown() && !session.isAuthorized()){
      presentationShownState.setShown(true)
      //openLoginActivity()
      return
    }
    setContentView(R.layout.activity_main)
    setupNavigation()

    viewModel.run {
      uiLiveData.observe(this@MainActivity, Observer { render(it) })
    }
  }
  override fun onDestroy() {
    presentationShownState.setShown(false)
    super.onDestroy()
  }

  private fun setupNavigation() {
    navigationView.setOnNavigationItemSelectedListener { item ->
      if (item.itemId != R.id.menuBrowse && !session.isAuthorized()) {
        showContentUnavailableDialog()
        return@setOnNavigationItemSelectedListener false
      }

      if (navigationView.selectedItemId == item.itemId) {
        doForFragments { (it as? OnTabReselectedListener)?.onTabReselected() }
        return@setOnNavigationItemSelectedListener true
      }

      val target = when (item.itemId) {
        R.id.menuBrowse -> R.id.actionNavigateBrowseFragment
        R.id.menuProfile ->  R.id.actionNavigateBrowseFragment
        R.id.menuMessage ->  R.id.actionNavigateBrowseFragment
        else -> throw IllegalStateException("Unsupported menu item.")
      }

      navigationHost.findNavController().navigate(target)

      true
    }
  }

  private fun showContentUnavailableDialog() {
    showInfoDialog(
      title = getString(R.string.authenticatedOnlyDialogTitle),
      message = getString(R.string.authenticatedOnlyDialogMessage)
    )
  }

  private fun doForFragments(action: (Fragment) -> Unit) {
    navigationHost.findNavController().currentDestination?.id?.let {
      val navHost = supportFragmentManager.findFragmentById(R.id.navigationHost)
      navHost?.childFragmentManager?.primaryNavigationFragment?.let {
        action(it)
      }
    }
  }

  fun hideNavigation() {
    val height = dimenToPx(R.dimen.bottomNavigationHeight).toFloat()
    navigationViewWrapper.animate().setDuration(150).translationYBy(height).start()
  }

  fun showNavigation() {
    navigationViewWrapper.animate().setDuration(100).translationY(0F).start()
  }


  fun openLoginActivity() {
    startActivity(Intent(this, LoginActivity::class.java).apply {
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    })
  }

  private fun render(uiModel: MainUiModel) {
    uiModel.run {

    }
  }
}
