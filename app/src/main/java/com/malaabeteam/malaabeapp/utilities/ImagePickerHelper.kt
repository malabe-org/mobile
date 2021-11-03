package com.malaabeteam.malaabeapp.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.dhaval2404.imagepicker.ImagePicker
import com.malaabeteam.malaabeapp.utilities.extensions.onBitmapError
import com.malaabeteam.malaabeapp.utilities.extensions.onBitmapSuccess
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import okhttp3.MultipartBody

import okhttp3.RequestBody
import timber.log.Timber


object ImagePickerHelper {


  const val PICKER_CODE = 333
  private const val maxSizeKb = 2048
  private const val maxDimen = 2048

  fun openGalleryOrCamera(fragment: Fragment) {
    ImagePicker.with(fragment)
      .compress(maxSizeKb)
      .maxResultSize(maxDimen, maxDimen)
      .start(PICKER_CODE)
  }

  fun openGalleryOnly(fragment: Fragment) {
    ImagePicker.with(fragment)
      .galleryOnly()
      .compress(maxSizeKb)
      .maxResultSize(maxDimen, maxDimen)
      .start(PICKER_CODE)
  }

  fun openCropView(fragment: Fragment, uri: Uri, cropShape: CropImageView.CropShape) {
    CropImage.activity(uri)
      .setAspectRatio(1, 1)
      .setFixAspectRatio(true)
      .setCropShape(cropShape)
      .start(fragment.requireContext(), fragment)
  }

  fun onPickResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?,
    onSuccess: (Uri) -> Unit = {},
    onError: () -> Unit = {}
  ) {
    when {
      requestCode == PICKER_CODE && resultCode == Activity.RESULT_OK -> {
        data?.data?.let { onSuccess(it) } ?: onError()
      }
      resultCode == ImagePicker.RESULT_ERROR -> onError()
    }
  }

  fun onCropResult(
    context: Context,
    requestCode: Int,
    resultCode: Int,
    data: Intent?,
    onSuccess: (Bitmap) -> Unit,
    onError: () -> Unit,
    makeCircular: Boolean = false
  ) {
    val transformation = if (makeCircular) arrayOf(RoundedCorners(maxDimen)) else arrayOf()

    when {
      requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK -> {
        val uri = CropImage.getActivityResult(data).uri
        Glide.with(context)
          .asBitmap()
          .load(uri)
          .transform(*transformation)
          .onBitmapSuccess { it?.let(onSuccess) ?: onError() }
          .onBitmapError { onError() }
          .submit()
      }
      resultCode == ImagePicker.RESULT_ERROR -> onError()
    }
  }
}
