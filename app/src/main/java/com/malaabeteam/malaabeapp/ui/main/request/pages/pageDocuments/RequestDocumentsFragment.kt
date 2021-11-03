package com.malaabeteam.malaabeapp.ui.main.request.pages.pageDocuments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.malaabeteam.common_ui.extensions.*
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.main.request.DocumentFragmentSheet
import com.malaabeteam.malaabeapp.ui.main.request.DocumentViewModel
import com.malaabeteam.malaabeapp.ui.main.request.OnPageValidationListener
import com.malaabeteam.malaabeapp.ui.main.request.helpers.DocumentFormData
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage
import com.malaabeteam.malaabeapp.ui.main.request.views.AddAnotherDocumentView
import com.malaabeteam.malaabeapp.utilities.ImagePickerHelper
import com.malaabeteam.malaabeapp.utilities.extensions.dimenToPx
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_document.*
import kotlinx.android.synthetic.main.fragment_request_documents.*

class RequestDocumentsFragment : BaseFragment<RequestDocumentsViewModel>(R.layout.fragment_request_documents) {
  override val viewModel by viewModels<RequestDocumentsViewModel> { viewModelFactory }
  private val parentViewModel by viewModels<DocumentViewModel>({ requireParentFragment() }) { viewModelFactory }

  override fun onAttach(context: Context) {
    fragmentComponent().inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView()
    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
      infoLiveData.observe(viewLifecycleOwner, Observer { renderInfo(getString(it)) })
    }
    parentViewModel.editLiveData.observe(viewLifecycleOwner, Observer {
      viewModel.run {
        //isEditMode = true
        //loadInitialPhotos(it.documents.images, requireContext().applicationContext)
      }
    })
  }

  private fun setupView() {
    fragmentRequestPhotosMainImage.clipToOutline = true
    fragmentRequestPhotosMainImageHolder.onClick { ImagePickerHelper.openGalleryOrCamera(this) }
    fragmentRequestPhotosMainImageRemove.onClick { viewModel.removeMainPhoto() }
  }



  override fun onResume() {
    super.onResume()
    viewModel.validate()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    when (requestCode) {
      ImagePickerHelper.PICKER_CODE -> {
        ImagePickerHelper.onPickResult(
          requestCode, resultCode, data,
          onSuccess = {
            ImagePickerHelper.openCropView(
              fragment = this,
              uri = it,
              cropShape = CropImageView.CropShape.RECTANGLE
            )
          },
          onError = {
            renderError(getString(R.string.errorImagePermissions))
          }
        )
      }
      CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
        ImagePickerHelper.onCropResult(requireContext(), requestCode, resultCode, data,
          makeCircular = false,
          onSuccess = {
            viewModel.addDoc(DocumentFormData(image = it))
          },
          onError = {
            renderError(getString(R.string.errorCouldNotCropImage))
          })
      }
    }
  }

  private fun renderInfo(message: String) {
    (parentFragment as DocumentFragmentSheet)
      .fragmentRequestSnackbarHost
      .showInfoSnackbar(message)
  }

  private fun renderError(errorMessage: String) {
    (parentFragment as DocumentFragmentSheet)
      .fragmentRequestSnackbarHost
      .showErrorSnackbar(errorMessage)
  }

  private fun render(model: RequestDocumentsUiModel) {
    model.run {
        docs?.let {
          parentViewModel.updateDocumentData(docs)
          renderPhotos(docs)
        }

      isValid?.let {
        (parentFragment as OnPageValidationListener).validatePage(RequestPage.DOCUMENTS, it)
      }
    }
  }

  private fun renderPhotos(photos: List<DocumentFormData>) {
    fragmentRequestPhotosGrid.removeAllViews()
    fragmentRequestPhotosMainImageWrapper.visibleIf(photos.isNotEmpty())
    fragmentRequestPhotosMainImageHolder.visibleIf(photos.isEmpty())
    if (photos.isNotEmpty()) {
      tvImage.visibility=VISIBLE
      tvImage.text = "CNI fle:"
      Glide.with(this@RequestDocumentsFragment)
        .load(photos.first().image)
        .into(fragmentRequestPhotosMainImage)

      photos
        .takeLast(photos.size - 1)
        .forEach { photo ->
          val view = createAddPhotoView(photo)
          fragmentRequestPhotosGrid.addView(view)
        }
      fragmentRequestPhotosGrid.addView(createAddPhotoView().apply {
        onClick { ImagePickerHelper.openGalleryOrCamera(this@RequestDocumentsFragment) }
      })
    }
  }

  private fun createAddPhotoView(photo: DocumentFormData? = null) =
    AddAnotherDocumentView(requireContext()).apply {
      val marginMedium = dimenToPx(R.dimen.spacingMedium)
      val marginSmall = dimenToPx(R.dimen.spacingSmall)
      val size = (screenWidth() - 3 * marginMedium) / 2
      layoutParams = FrameLayout.LayoutParams(size, size).apply {
        setMargins(marginSmall)
      }
      photo?.let { photo ->
        setPhoto(photo)
        onRemoveClickListener = { viewModel.removePhoto(it) }
      }
    }
  companion object {
    fun create() = RequestDocumentsFragment()
  }
}
