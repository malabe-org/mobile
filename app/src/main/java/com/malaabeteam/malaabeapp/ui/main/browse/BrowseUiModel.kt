package com.malaabeteam.malaabeapp.ui.main.browse

import com.malaabeteam.malaabeapp.ui.common.UiModel
import com.malaabeteam.malaabeapp.ui.main.browse.recycler.DocumentListItem

data class BrowseUiModel(
  val documents: List<DocumentListItem>? = null,
  val isLoading: Boolean? = null
): UiModel() {
  override fun update(newModel: UiModel)=
    (newModel as BrowseUiModel).copy(
      documents = newModel.documents ?: documents,
      isLoading = newModel.isLoading ?: isLoading
    )
}
