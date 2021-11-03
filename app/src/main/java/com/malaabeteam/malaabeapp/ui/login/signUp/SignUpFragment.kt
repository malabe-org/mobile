package com.malaabeteam.malaabeapp.ui.login.signUp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.core.text.HtmlCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.common_ui.extensions.setErrorMessage
import com.malaabeteam.common_ui.extensions.showErrorSnackbar
import com.malaabeteam.common_ui.extensions.visibleIf
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.model.Gender
import com.malaabeteam.malaabeapp.loginFragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.view_loading.*


class SignUpFragment : BaseFragment<SignUpViewModel>(R.layout.fragment_sign_up) {
  override val viewModel by viewModels<SignUpViewModel> { viewModelFactory }
  lateinit var userGender: String

  override fun onAttach(context: Context) {
    loginFragmentComponent().inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    signUpFooter.setOnClickListener { findNavController().navigate(R.id.actionSignUpToSignIn) }
    signUpFooter.text = HtmlCompat.fromHtml(getString(R.string.signUpFooterText), HtmlCompat.FROM_HTML_MODE_LEGACY)

    signUpFirstNameInput.doOnTextChanged { _, _, _, _ -> viewModel.validateUserName(signUpFirstNameInput.text.toString()) }
    signUpLastNameInput.doOnTextChanged { _, _, _, _ -> viewModel.validateUserName(signUpLastNameInput.text.toString()) }
    signUpEmailInput.doOnTextChanged { _, _, _, _ -> viewModel.validateEmail(signUpEmailInput.text.toString()) }
    signUpPasswordInput.doOnTextChanged { _, _, _, _ -> viewModel.validatePassword(signUpPasswordInput.text.toString()) }

    signUpGenderChoice.editText?.onClick{
      showGenderPicker(Gender.MEN)
    }

    signUpButton.onClick {
      viewModel.signUp(
        email = signUpEmailInput.text.toString(),
        password = signUpPasswordInput.text.toString(),
        firstname = signUpFirstNameInput.text.toString(),
        lastname = signUpLastNameInput.text.toString(),
        phone = signUpPhoneInput.text.toString(),
        gender = userGender
      )
    }

    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
    }
  }

  private fun showGenderPicker(current: Gender?) {
    val values = Gender.values()
    var selectedIndex = values.indexOf(current)
    MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
      .apply {
        background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_dialog_white)
        setSingleChoiceItems(
          values.map { getString(it.displayName) }.toList().toTypedArray(),
          selectedIndex
        ) { _, selected -> selectedIndex = selected }

        setPositiveButton(R.string.textConfirm) { _, _ ->
          if (selectedIndex != -1) userGender = values[selectedIndex].apiName
          signUpGender.setText(values[selectedIndex].displayName)
        }
        setNegativeButton(R.string.textCancel) { _, _ -> }

        show()
      }
  }

  private fun render(uiModel: SignUpUiModel) {
    uiModel.run {
      signUpButton.isEnabled = emailValid ?: false && passwordValid ?: false && firstname ?: false
      isLoggedIn?.let { if (it) (activity as LoginActivity).openMainActivity() }
      isLoading?.let { loadingView.visibleIf(it) }
      emailValid?.let { signUpEmailLayout.setErrorMessage(if (it) null else getString(R.string.signUpErrorInvalidEmail)) }
      passwordValid?.let { signUpPasswordLayout.setErrorMessage(if (it) null else getString(R.string.signUpErrorInvalidPassword)) }
      firstname?.let { signUpFirstNameLayout.setErrorMessage(if (it) null else getString(R.string.signUpErrorInvalidUserName)) }
      errorMessage?.let { requireView().showErrorSnackbar(it) }
    }
  }

}
