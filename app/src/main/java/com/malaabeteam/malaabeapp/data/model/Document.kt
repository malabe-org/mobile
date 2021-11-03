package com.malaabeteam.malaabeapp.data.model

import com.malaabeteam.network.model.RequestUser

data class Document(
  val requests: List<RequestUser>
){
  fun getMainDoc() = requests.firstOrNull()?.documents ?: ""
}
