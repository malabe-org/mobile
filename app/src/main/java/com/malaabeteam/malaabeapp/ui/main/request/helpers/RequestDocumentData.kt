package com.malaabeteam.malaabeapp.ui.main.request.helpers

data class RequestDocumentData(
  val description: DescriptionFormData = DescriptionFormData(),
  val documents: List<DocumentFormData> = emptyList()
){
  fun isEmpty(): Boolean {
    if (documents.isNotEmpty()) return false
    if (!description.isEmpty()) return false
    return true
  }
}
