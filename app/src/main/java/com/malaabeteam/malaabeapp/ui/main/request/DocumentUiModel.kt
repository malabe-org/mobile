package com.malaabeteam.malaabeapp.ui.main.request

import com.malaabeteam.malaabeapp.data.model.Document
import com.malaabeteam.malaabeapp.ui.common.UiModel
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage

data class DocumentUiModel(
  val page: RequestPage? = null,
  val nextStepEnabled: Boolean? = null,
  val isLoading: Boolean? = null,
  val isLoadingText: Int? = null,
  val isFormCompleted: Boolean? = null,
  val isPublished: Boolean? = null,
  val initialDocument: Document? = null,
  val localizedError: String? = null
): UiModel() {

  override fun update(newModel: UiModel) =
    (newModel as DocumentUiModel).copy(
      page = newModel.page ?: page,
      nextStepEnabled = newModel.nextStepEnabled ?: nextStepEnabled,
      isLoading = newModel.isLoading ?: isLoading,
      isLoadingText = newModel.isLoadingText ?: isLoadingText
    )
}

