package com.malaabeteam.malaabeapp.ui.login.signUp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.core.text.HtmlCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.common_ui.extensions.setErrorMessage
import com.malaabeteam.common_ui.extensions.showErrorSnackbar
import com.malaabeteam.common_ui.extensions.visibleIf
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.loginFragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.view_loading.*


class SignUpFragment : BaseFragment<SignUpViewModel>(R.layout.fragment_sign_up) {
  override val viewModel by viewModels<SignUpViewModel> { viewModelFactory }

  override fun onAttach(context: Context) {
    loginFragmentComponent().inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    signUpFooter.setOnClickListener { findNavController().navigate(R.id.actionSignUpToSignIn) }
    signUpFooter.text = HtmlCompat.fromHtml(getString(R.string.signUpFooterText), HtmlCompat.FROM_HTML_MODE_LEGACY)

    signUpUserNameInput.doOnTextChanged { _, _, _, _ -> viewModel.validateUserName(signUpUserNameInput.text.toString()) }
    signUpEmailInput.doOnTextChanged { _, _, _, _ -> viewModel.validateEmail(signUpEmailInput.text.toString()) }
    signUpPasswordInput.doOnTextChanged { _, _, _, _ -> viewModel.validatePassword(signUpPasswordInput.text.toString()) }
    signUpAgreeTerms.setOnCheckedChangeListener { _, isChecked -> viewModel.checkTerms(isChecked) }

    signUpButton.onClick {
      viewModel.signUp(
        email = signUpEmailInput.text.toString(),
        userName = signUpUserNameInput.text.toString(),
        password = signUpPasswordInput.text.toString()
      )
    }

    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
    }
  }
  private fun render(uiModel: SignUpUiModel) {
    uiModel.run {
      signUpButton.isEnabled = termsAccepted ?: false && emailValid ?: false && passwordValid ?: false && userNameValid ?: false
      isLoggedIn?.let { if (it) (activity as LoginActivity).openMainActivity() }
      isLoading?.let { loadingView.visibleIf(it) }
      emailValid?.let { signUpEmailLayout.setErrorMessage(if (it) null else getString(R.string.signUpErrorInvalidEmail)) }
      passwordValid?.let { signUpPasswordLayout.setErrorMessage(if (it) null else getString(R.string.signUpErrorInvalidPassword)) }
      userNameValid?.let { signUpUserNameLayout.setErrorMessage(if (it) null else getString(R.string.signUpErrorInvalidUserName)) }
      errorMessage?.let { requireView().showErrorSnackbar(it) }
    }
  }

}
