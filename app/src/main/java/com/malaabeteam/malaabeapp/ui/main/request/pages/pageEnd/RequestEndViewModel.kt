package com.malaabeteam.malaabeapp.ui.main.request.pages.pageEnd

import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import javax.inject.Inject

class RequestEndViewModel @Inject constructor(): BaseViewModel<RequestEndUiModel>() {
  init {
    uiState = RequestEndUiModel(isLoading = false, isValid=true)
  }
  fun validate() {
    if (uiState?.isLoading == true) {
      uiState = RequestEndUiModel(isValid = false)
      return
    }else {
      RequestEndUiModel(isValid = true)
    }
  }
  private fun onError() {
    _errorLiveData.value = R.string.errorGeneral
  }
}
