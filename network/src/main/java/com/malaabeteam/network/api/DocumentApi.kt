package com.malaabeteam.network.api

import com.malaabeteam.network.model.request.DocumentBody
import com.malaabeteam.network.service.DocumentService
import javax.inject.Inject

class DocumentApi @Inject constructor(
  private val service: DocumentService
) {

  suspend fun fetchDhHub(token: String) =
    service.fetchDhHub(token)

  suspend fun fetchDocument(documentId: String) =
    service.fetchDocumentDetails(documentId)

//  suspend fun fetchDocuments(
//    session: String,
//    pageSize: Int,
//    first: Int
//  ) = service.fetchDocuments(session, pageSize, first)

  suspend fun fetchUserRequest(
    token: String
  ) = service.fetchUserRequest(token)

  suspend fun postDocument(
    session: String,
    userId: String
  ) = service.postDocument(userId, session, DocumentBody(emptyList()))

//  suspend fun uploadDocPhoto(
//    files: MultipartBody
//  ) = service.uploadProductImage(files)
}
