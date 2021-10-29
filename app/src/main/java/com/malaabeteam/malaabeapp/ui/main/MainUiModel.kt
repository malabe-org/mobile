package com.malaabeteam.malaabeapp.ui.main

import com.malaabeteam.malaabeapp.ui.common.UiModel

data class MainUiModel(
  val hasDocInProcess:  Boolean? = null
) : UiModel() {

  override fun update(newModel: UiModel) =
    (newModel as MainUiModel).copy(
      hasDocInProcess = newModel.hasDocInProcess ?: hasDocInProcess
    )
}
