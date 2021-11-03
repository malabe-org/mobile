package com.malaabeteam.malaabeapp.ui.main.request.pages.pageDocuments

import com.malaabeteam.malaabeapp.ui.common.UiModel
import com.malaabeteam.malaabeapp.ui.main.request.helpers.DocumentFormData

data class RequestDocumentsUiModel(
  val docs: List<DocumentFormData>? = null,
  val isValid: Boolean? = null,
  val isLoading: Boolean? = null
): UiModel() {
  override fun update(newModel: UiModel) =
    (newModel as RequestDocumentsUiModel).copy(
      docs = newModel.docs ?: docs,
      isValid = newModel.isValid ?: isValid,
      isLoading = newModel.isLoading ?: isLoading
    )
}
