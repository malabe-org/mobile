package com.malaabeteam.malaabeapp.ui.main.request.pages.pageDescription

import androidx.lifecycle.viewModelScope
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.network.model.Dhub
import com.malaabeteam.malaabeapp.data.repository.DocumentRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.ui.main.browse.BrowseUiModel
import com.malaabeteam.malaabeapp.ui.main.request.helpers.DescriptionFormData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RequestDescriptionViewModel @Inject constructor(
  private val documentRepository: DocumentRepository
): BaseViewModel<RequestDescriptionUiModel>() {
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

  fun setDhub(dhub: Dhub){
    requestDescription = requestDescription.copy(dhHub = dhub)
    validate()
  }

  fun loadDhub(){

  viewModelScope.launch {
    try {
        uiState = RequestDescriptionUiModel(isLoading = true, loadingDhHub = true)
      val dhubs = documentRepository.LoadDhub()
      uiState = RequestDescriptionUiModel(isLoading=false, loadingDhHub=false, dHubs=dhubs, loadedDhubs=dhubs.dhHubs)
    }catch (e: Throwable){
      onError(e)
    }
  }
  }


  private fun onError(error: Throwable) {
    uiState = RequestDescriptionUiModel(isLoading = false)
    _errorLiveData.value = R.string.errorGeneral
    Timber.w(error)
  }

  override fun onCleared() {
    super.onCleared()
  }
}
