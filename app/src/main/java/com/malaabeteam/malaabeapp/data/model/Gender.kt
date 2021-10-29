package com.malaabeteam.malaabeapp.data.model

import androidx.annotation.DrawableRes
import com.malaabeteam.malaabeapp.R

enum class Gender(val apiName: String, @DrawableRes val icon: Int) {
  MALE("Male", R.drawable.ic_gender_male),
  FEMALE("Female", R.drawable.ic_gender_female),
  OTHER("Other", R.drawable.ic_gender_male)
}
