package com.malaabeteam.malaabeapp.ui.main.request.pages.pageEnd

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.malaabeteam.common_ui.extensions.showErrorSnackbar
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.main.request.DocumentFragmentSheet
import com.malaabeteam.malaabeapp.ui.main.request.DocumentViewModel
import com.malaabeteam.malaabeapp.ui.main.request.OnPageValidationListener
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage
import kotlinx.android.synthetic.main.fragment_document.*


class RequestEndFragment : BaseFragment<RequestEndViewModel>(R.layout.fragment_request_end) {
  override val viewModel by viewModels<RequestEndViewModel> { viewModelFactory }
  private val parentViewModel by viewModels<DocumentViewModel>({ requireParentFragment() }) { viewModelFactory }

  override fun onAttach(context: Context) {
    fragmentComponent().inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
      errorLiveData.observe(viewLifecycleOwner, Observer { renderError(getString(it)) })
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.validate()
  }

  private fun render(model: RequestEndUiModel){
    model.run{
      isValid?.let {
        (parentFragment as OnPageValidationListener).validatePage(RequestPage.END, it)
      }
    }
  }

  private fun renderError(errorMessage: String) {
    (parentFragment as DocumentFragmentSheet)
      .fragmentRequestSnackbarHost
      .showErrorSnackbar(errorMessage)
  }

  companion object {
     fun create() = RequestEndFragment()
   }
}
