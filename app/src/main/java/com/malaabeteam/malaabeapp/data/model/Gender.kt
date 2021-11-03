package com.malaabeteam.malaabeapp.data.model

import android.content.Context
import androidx.annotation.StringRes
import com.malaabeteam.malaabeapp.R

enum class Gender(
  @StringRes val displayName: Int,
  val apiName: String
) {
  MEN(R.string.textMen, "Male"),
  WOMEN(R.string.textWomen, "Female");
}

