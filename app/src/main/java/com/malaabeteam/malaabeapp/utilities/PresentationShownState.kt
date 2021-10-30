package com.malaabeteam.malaabeapp.utilities

import android.content.Context
import com.malaabeteam.persistance.BaseStorage
import javax.inject.Inject

class PresentationShownState @Inject constructor(context: Context) : BaseStorage(context) {

  override val storageName = "TutorialShownState"

  companion object {
    private const val KEY_SHOWN = "KEY_SHOWN"
  }

  fun wasShown() = getBoolean(KEY_SHOWN)

  fun setShown(hasShown: Boolean) = putBoolean(KEY_SHOWN, hasShown)
}
