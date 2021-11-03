package com.malaabeteam.malaabeapp.ui.main.request

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.malaabeteam.common_ui.extensions.*
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.repository.MainMotor
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseSheetFragment
import com.malaabeteam.malaabeapp.ui.main.MainActivity
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPagesAdapter
import com.malaabeteam.persistance.UserSession
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_document.*
import javax.inject.Inject

class DocumentFragmentSheet: BaseSheetFragment<DocumentViewModel>(), OnPageValidationListener {
  companion object {
    const val TAG = "DocumentFragment"
    private const val ARG_PRODUCT_ID = "ARG_DOCUMENT_ID"

    fun create(productId: Long? = null): DocumentFragmentSheet {
      val bundle = bundleOf(ARG_PRODUCT_ID to productId)
      return DocumentFragmentSheet().apply {
        arguments = bundle
      }
    }
  }
  override val layoutResId = R.layout.fragment_document
  override val viewModel by viewModels<DocumentViewModel> { viewModelFactory }
  private val motor: MainMotor by viewModels()

  @Inject
  lateinit var session: UserSession

  override fun onAttach(context: Context) {
    fragmentComponent().inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView()
    setupPages()
    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
      errorLiveData.observe(viewLifecycleOwner, Observer { renderError(it) })

    }
  }

  private fun setupView() {
    isCancelable = false
    fragmentRequestCloseIcon.onClick {
      if (viewModel.isRequestDataEmpty()) {
        dismiss()
        return@onClick
      }
      showConfirmDialog(
        title = getString(R.string.requestCloseConfirmationTitle),
        message = getString(R.string.requestCloseConfirmationText),
        onAccept = { dismiss() }
      )
    }
    fragmentRequestNextButton.onClick { viewModel.nextPage() }
    //if (isEditMode) fragmentRequestTitle.setText(R.string.sellEditTitle)
  }

  private fun setupPages() {
    fragmentRequestPager.run {
      adapter = RequestPagesAdapter(this@DocumentFragmentSheet)
      isUserInputEnabled = true
      offscreenPageLimit = RequestPage.values().size - 1
    }
    fragmentRequestIndicator.run {
      setPage(RequestPage.DOCUMENTS)
      onPageClickListener = { viewModel.changePage(it) }
    }
  }

  private fun onFormCompleted() {
    val title = R.string.requestPublishConfirmationTitle
    val message = R.string.requestPublishConfirmationText
    showConfirmDialog(
      title = getString(title),
      message = getString(message),
      onAccept = {
        //motor.upload(viewModel.getData(this.requireContext()),session.checkAuthorization()!!)
        viewModel.publishProduct(requireContext().applicationContext, false)
      }
    )
  }

  private fun render(model: DocumentUiModel) {
    model.run {
      page?.let {
        if (fragmentRequestPager.currentItem != it.index) {
          fragmentRequestPager.currentItem = it.index
          fragmentRequestIndicator.setPage(it)
          fragmentRequestScroll.smoothScrollTo(0, 0)
          val buttonText = when (it) {
            RequestPage.END -> R.string.requestCompleteForm
            else -> R.string.requestNextStep
          }
          fragmentRequestNextButton.setText(buttonText)
        }
      }
      isFormCompleted?.let { onFormCompleted() }
      isLoadingText?.let { fragmentRequestProgressText.text = getString(it) }
      isPublished?.let {
        (requireActivity() as MainActivity).run {
          mainActivitySnackbarHost.showInfoSnackbar(getString(R.string.infoRequestCreated))
          refreshTabs()
        }
        dismiss()
      }
      isLoading?.let {
        fragmentRequestProgress.visibleIf(it)
        fragmentRequestScroll.visibleIf(!it, gone = false)
      }
      localizedError?.let { fragmentRequestSnackbarHost.showErrorSnackbar(it) }
      nextStepEnabled?.let { fragmentRequestNextButton.isEnabled = it }
    }
  }

  private fun renderError(messageResId: Int) {
    fragmentRequestSnackbarHost.showErrorSnackbar(getString(messageResId))
  }
  override fun validatePage(page: RequestPage, isValid: Boolean) = viewModel.validatePage(page, true)
}
