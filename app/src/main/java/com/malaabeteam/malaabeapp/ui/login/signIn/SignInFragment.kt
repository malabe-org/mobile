package com.malaabeteam.malaabeapp.ui.login.signIn

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.core.text.HtmlCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.malaabeteam.common_ui.extensions.*
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.loginFragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.view_loading.*


class SignInFragment : BaseFragment<SignInViewModel>(R.layout.fragment_sign_in) {

  override val viewModel by viewModels<SignInViewModel> { viewModelFactory }

  override fun onAttach(context: Context) {
    loginFragmentComponent().inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    signInFooter.setOnClickListener { findNavController().navigate(R.id.actionSignInToSignUp) }
    signInFooter.text = HtmlCompat.fromHtml(getString(R.string.signInFooterText), HtmlCompat.FROM_HTML_MODE_LEGACY)

    signInEmailInput.doOnTextChanged { _, _, _, _ -> viewModel.validateEmail(signInEmailInput.text.toString()) }
    signInPasswordInput.doOnTextChanged { _, _, _, _ -> viewModel.validatePassword(signInPasswordInput.text.toString()) }

    signInButton.onClick {
      viewModel.signIn(signInEmailInput.text.toString(), signInPasswordInput.text.toString())
    }

    viewModel.run {

      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
    }
  }

  override fun onResume() {
    super.onResume()
    signInEmailLayout.clearErrorIfEmpty()
    signInPasswordLayout.clearErrorIfEmpty()
  }

  private fun render(uiModel: SignInUiModel) {
    uiModel.run {
      signInButton.isEnabled = passwordValid ?: false && emailValid ?: false

      isLoggedIn?.let {
        if (isResetPassword == true) {
          //
        } else if (it) {
          (activity as LoginActivity).openMainActivity()
        }
      }
      isLoading?.let { loadingView.visibleIf(it) }
      emailValid?.let { signInEmailLayout.setErrorMessage(if (it) null else getString(R.string.signUpErrorInvalidEmail)) }
      passwordValid?.let { signInPasswordLayout.setErrorMessage(if (it) null else getString(R.string.signUpErrorInvalidPassword)) }
      errorMessage?.let { requireView().showErrorSnackbar(it) }
    }
  }

  companion object {

  }
}
