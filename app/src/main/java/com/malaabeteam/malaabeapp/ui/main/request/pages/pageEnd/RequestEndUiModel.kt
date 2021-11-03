package com.malaabeteam.malaabeapp.ui.main.request.pages.pageEnd

import com.malaabeteam.malaabeapp.ui.common.UiModel

data class RequestEndUiModel(
  val isLoading: Boolean? = null,
  val isValid: Boolean?=null
): UiModel(){
  override fun update(newModel: UiModel)=
    (newModel as RequestEndUiModel).copy(
      isLoading = newModel.isLoading ?: isLoading,
      isValid = newModel.isValid ?: isValid
    )
}
