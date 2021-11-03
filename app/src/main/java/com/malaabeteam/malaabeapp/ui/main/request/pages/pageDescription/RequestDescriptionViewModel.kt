package com.malaabeteam.malaabeapp.ui.main.request.pages.pageDescription

import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.ui.main.request.helpers.DescriptionFormData
import javax.inject.Inject

class RequestDescriptionViewModel @Inject constructor(): BaseViewModel<RequestDescriptionUiModel>() {
  private var requestDescription = DescriptionFormData()

  init {
    uiState = RequestDescriptionUiModel(isLoading = false, isValid = true)
  }

  fun setData(descriptionData: DescriptionFormData){
    requestDescription = descriptionData.copy()
    validate()
  }

  fun validate() {
    uiState = RequestDescriptionUiModel(
      isValid = true,
      requestDescription = requestDescription
    )
  }

  fun setDescription(description: String) {
    requestDescription = requestDescription.copy(description = description)
    validate()
  }

  fun setTitle(title: String) {
    requestDescription = requestDescription.copy(title = title)
    validate()
  }

  fun setTypeDocument(type: String) {
    requestDescription = requestDescription.copy(typeDocument = type)
    validate()
  }

  private fun onError() {
    _errorLiveData.value = R.string.errorGeneral
  }

  override fun onCleared() {
    super.onCleared()
  }
}
