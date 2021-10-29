package com.malaabeteam.network.api

import com.malaabeteam.network.model.request.DocumentBody
import com.malaabeteam.network.service.DocumentService
import javax.inject.Inject

class DocumentApi @Inject constructor(
  private val service: DocumentService
) {

  suspend fun fetchDocuments(
    session: String,
    pageSize: Int,
    first: Int
  ) = service.fetchDocuments(session, pageSize, first)

  suspend fun postDocument(
    session: String,
    userId: String,
    title: String,
    typeDocument: String,
    description: String
  ) = service.postDocument(userId, session, DocumentBody(title, typeDocument, description))

}
