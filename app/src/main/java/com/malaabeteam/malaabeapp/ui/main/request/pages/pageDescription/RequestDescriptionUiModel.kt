package com.malaabeteam.malaabeapp.ui.main.request.pages.pageDescription

import com.malaabeteam.malaabeapp.data.model.Dhubs
import com.malaabeteam.malaabeapp.ui.common.UiModel
import com.malaabeteam.malaabeapp.ui.main.request.helpers.DescriptionFormData
import com.malaabeteam.network.model.Dhub

data class RequestDescriptionUiModel(
  val requestDescription: DescriptionFormData? = null,
  val dHubs: Dhubs? = null,
  val loadingDhHub: Boolean? = null,
  val loadedDhubs: List<Dhub>?=null,
  val isValid: Boolean? = null,
  val isLoading: Boolean? = null
) : UiModel() {
  override fun update(newModel: UiModel) =
    (newModel as RequestDescriptionUiModel).copy(
      requestDescription = newModel.requestDescription ?: requestDescription,
      isValid = newModel.isValid ?: isValid,
      isLoading = newModel.isLoading ?: isLoading
    )
}
