package com.malaabeteam.malaabeapp.ui.main.request.pages.pageDescription

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.malaabeteam.common_ui.extensions.clearOnActionDone
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.common_ui.extensions.showInfoSnackbar
import com.malaabeteam.common_ui.extensions.visibleIf
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.network.model.Dhub
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.main.request.DocumentFragmentSheet
import com.malaabeteam.malaabeapp.ui.main.request.DocumentViewModel
import com.malaabeteam.malaabeapp.ui.main.request.OnPageValidationListener
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage
import kotlinx.android.synthetic.main.fragment_document.*
import kotlinx.android.synthetic.main.fragment_request_description.*
import kotlinx.coroutines.NonCancellable.isActive


class RequestDescriptionFragment : BaseFragment<RequestDescriptionViewModel>(R.layout.fragment_request_description) {
  override val viewModel by viewModels<RequestDescriptionViewModel> { viewModelFactory }
  private val parentViewModel by viewModels<DocumentViewModel>({ requireParentFragment() }) { viewModelFactory }
  private var textWatchersEnabled = true
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView()
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

  private fun setupView(){
    fragmentSellDescriptionName.editText?.run {
      doAfterTextChanged { if (textWatchersEnabled) viewModel.setDescription(it.toString()) }
      clearOnActionDone()
    }

    fragmentSellDescriptionBrand.editText?.onClick {
      viewModel.loadDhub()
    }
  }

  private fun showDhubPicker(brands: List<Dhub>) {
    val values = brands
    var selectedIndex = values.indexOf(values.first())
    val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
      .apply {
        background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_dialog_white)
        setSingleChoiceItems(
          values.map { it.address.region + " - " + it.address.department + " - " + it.address.city }.toList().toTypedArray(),
          selectedIndex
        ) { _, selected -> selectedIndex = selected }

        setPositiveButton(R.string.textConfirm) { _, _ ->
          if (selectedIndex != -1) viewModel.setDhub(values[selectedIndex])
          fragmentSellDescriptionBrand.editText?.setText(values[selectedIndex].address.city)
        }
        setNegativeButton(R.string.textCancel) { _, _ -> }

        show()
      }
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
      requestDescription?.let{
        parentViewModel.updateDescriptionData(it)
      }
      loadedDhubs?.let { showDhubPicker(it) }
      loadingDhHub?.let {
        fragmentSellDescriptionCategoryLoading.visibleIf(it)
      }
      isValid?.let {
        (parentFragment as OnPageValidationListener).validatePage(RequestPage.DESCRIPTION, it)
      }
    }
  }
}
