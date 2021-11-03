package com.malaabeteam.malaabeapp.data.model

import com.malaabeteam.network.model.ImageDto
import com.malaabeteam.network.model.response.TreatmentDto

data class DocUser(
  val seeker: String,
  val documents: ImageDto,
  val treatment: TreatmentDto
)
