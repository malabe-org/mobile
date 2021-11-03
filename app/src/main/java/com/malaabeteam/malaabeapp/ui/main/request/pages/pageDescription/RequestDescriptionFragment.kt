package com.malaabeteam.malaabeapp.ui.main.request.pages.pageDescription

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.malaabeteam.common_ui.extensions.showInfoSnackbar
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.main.request.DocumentFragmentSheet
import com.malaabeteam.malaabeapp.ui.main.request.DocumentViewModel
import com.malaabeteam.malaabeapp.ui.main.request.OnPageValidationListener
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage
import kotlinx.android.synthetic.main.fragment_document.*


class RequestDescriptionFragment : BaseFragment<RequestDescriptionViewModel>(R.layout.fragment_request_description) {
  override val viewModel by viewModels<RequestDescriptionViewModel> { viewModelFactory }
  private val parentViewModel by viewModels<DocumentViewModel>({ requireParentFragment() }) { viewModelFactory }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
      infoLiveData.observe(viewLifecycleOwner, Observer { renderInfo(getString(it)) })
    }
  }


  override fun onAttach(context: Context) {
    fragmentComponent().inject(this)
    super.onAttach(context)
  }

  override fun onResume() {
    super.onResume()
    viewModel.validate()
  }

  private fun renderInfo(message: String) {
    (parentFragment as DocumentFragmentSheet)
      .fragmentRequestSnackbarHost
      .showInfoSnackbar(message)
  }
  companion object {
    fun create() = RequestDescriptionFragment()
  }

  private fun render(model: RequestDescriptionUiModel) {
    model.run {
      isValid?.let {
        (parentFragment as OnPageValidationListener).validatePage(RequestPage.DESCRIPTION, it)
      }
    }
  }
}
