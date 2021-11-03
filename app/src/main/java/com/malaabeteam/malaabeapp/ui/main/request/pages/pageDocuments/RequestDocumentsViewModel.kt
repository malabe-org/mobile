package com.malaabeteam.malaabeapp.ui.main.request.pages.pageDocuments

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.repository.DocumentRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.ui.main.request.helpers.DocumentFormData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class RequestDocumentsViewModel@Inject constructor(
  private val documentRepository: DocumentRepository
) : BaseViewModel<RequestDocumentsUiModel>() {
  private val docs = mutableListOf<DocumentFormData>()
  fun getDocs(): MutableList<DocumentFormData>{
    return docs
  }
  var isEditMode = false

  fun addDoc(photo: DocumentFormData) {
    viewModelScope.launch {
      docs.add(photo)
      validate()
    }
  }

  fun removePhoto(photo: DocumentFormData) {
    viewModelScope.launch {
      docs.remove(photo)
      validate()
      if (isEditMode) {
        //productsRepository.deleteProductPhoto(photo.productId!!, photo.photoId!!)
        _infoLiveData.value = R.string.requestEditRemoveDocText
      }
    }
  }

  fun removeMainPhoto() {
    viewModelScope.launch {
      if (docs.isNotEmpty()) {
        val doc = docs.removeAt(0)
        validate()
        if (isEditMode) {
          //documentRepository.deleteProductPhoto(photo.productId!!, photo.photoId!!)
          _infoLiveData.value = R.string.requestEditRemoveDocText
        }
      }
    }
  }

  fun loadInitialPhotos(docs: MutableList<Bitmap>?, context: Context) {
    viewModelScope.launch {
      docs?.forEach {
          getImageUri(context, it)?.path?.let { it1 -> loadAsBitmap(context, it1) }
      }
    }
  }

  private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 85, bytes)
    val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
    return Uri.parse(path)
  }

  private suspend fun loadAsBitmap(
    context: Context,
    img: String
  ) {
    val bitmap = withContext(Dispatchers.Default) {
      Glide.with(context).asBitmap().load(img).submit().get()
    }
    addDoc(DocumentFormData(bitmap))
  }

  fun validate(){
    Timber.d("-------------SIZE: $docs")
    uiState = RequestDocumentsUiModel(docs = docs, isValid = (docs.isNotEmpty() && docs.size == 3))
  }
}
