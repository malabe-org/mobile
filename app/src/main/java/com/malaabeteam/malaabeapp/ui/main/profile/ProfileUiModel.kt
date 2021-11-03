package com.malaabeteam.malaabeapp.ui.main.profile

import android.graphics.Bitmap
import com.malaabeteam.malaabeapp.data.model.User
import com.malaabeteam.malaabeapp.ui.common.UiModel

data class ProfileUiModel(
  val isLoading: Boolean? = null,
  val user: User? = null,
  val notAuthorized: Boolean? = null,
  val loadingPicture: Boolean? = null,
  val pictureLoaded: Bitmap? = null
) : UiModel() {

  override fun update(newModel: UiModel) =
    (newModel as ProfileUiModel).copy(
      isLoading = newModel.isLoading ?: isLoading,
      user = newModel.user ?: user,
      notAuthorized = newModel.notAuthorized ?: notAuthorized,
      pictureLoaded = newModel.pictureLoaded ?: pictureLoaded
    )
}
