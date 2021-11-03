package com.malaabeteam.malaabeapp.ui.main.request.helpers

import com.malaabeteam.malaabeapp.data.model.DocUser
import com.malaabeteam.malaabeapp.data.model.Document
import com.malaabeteam.network.model.Dhub
import org.threeten.bp.LocalDateTime

data class DescriptionFormData(
  val description: String = "",
  val dhHub: Dhub? = null
){
  fun isValid() = when{
    description.trim().isEmpty() -> false
    else -> true
  }

  fun isEmpty() = when{
    description.trim().isNotEmpty() -> false
    else -> true
  }

  companion object {
//    fun fromRequest(document: Document) = DescriptionFormData(
//      description = "",
//      dhHub = null
//    )
  }
}
