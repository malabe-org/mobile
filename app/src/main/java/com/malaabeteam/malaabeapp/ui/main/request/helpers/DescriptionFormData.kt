package com.malaabeteam.malaabeapp.ui.main.request.helpers

import com.malaabeteam.malaabeapp.data.model.DocUser
import com.malaabeteam.malaabeapp.data.model.Document
import org.threeten.bp.LocalDateTime

data class DescriptionFormData(
  val title: String = "",
  val typeDocument: String = "",
  val description: String = ""
){
  fun isValid() = when{
    title.trim().isEmpty() -> false
    typeDocument.trim().isEmpty() -> false
    description.trim().isEmpty() -> false
    else -> true
  }

  fun isEmpty() = when{
    title.trim().isNotEmpty() -> false
    typeDocument.trim().isNotEmpty() -> false
    description.trim().isNotEmpty() -> false
    else -> true
  }

  companion object {
    fun fromRequest(document: Document) = DescriptionFormData(
      title = "",
      typeDocument = "",
      description = ""
    )
  }
}
