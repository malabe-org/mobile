package com.malaabeteam.malaabeapp.data.repository

import com.malaabeteam.malaabeapp.data.mappers.Mappers
import com.malaabeteam.malaabeapp.data.model.Document
import com.malaabeteam.malaabeapp.di.scope.AppScope
import com.malaabeteam.network.MalaabeApi
import com.malaabeteam.persistance.UserSession
import javax.inject.Inject

@AppScope
class DocumentRepository @Inject constructor(
  private val api: MalaabeApi,
  private val session: UserSession,
  private val mappers: Mappers
) {
  companion object {
    private const val PAGE_SIZE = 10
  }

  private val allDocuments = mutableListOf<Document>()
  var allLoaded = false

  suspend fun loadDocuments(page: Int = 1): List<Document>{
    val token = session.checkAuthorization()
    val result = api.document.fetchDocuments(token,
      PAGE_SIZE, (page * PAGE_SIZE) - PAGE_SIZE
    ).paylod?.map{
      mappers.document.fromNetwork(it)
    } ?: emptyList()
    if (result.isNotEmpty()){
      allLoaded = true
    }
    return allDocuments.apply{addAll(result)}
  }

  fun hasDocInProcess(): Boolean {
    return allLoaded
  }

  suspend fun postDocument(
    userId: String,
    title: String,
    typeDocument: String,
    description: String
  ): Any {
    val token = session.checkAuthorization()

    return api.document.postDocument(token, userId, title, typeDocument, description)
  }
}
