package com.malaabeteam.network.model

import com.malaabeteam.network.model.response.TreatmentDto

data class RequestUser(
  val _id: String,
  val seeker: String,
  val documents: ImageDto,
  val treatment: TreatmentDto
)
