package com.malaabeteam.network.service


import com.malaabeteam.network.model.DocumentDto
import com.malaabeteam.network.model.request.DocumentBody
import com.malaabeteam.network.model.response.ItemResponse
import com.malaabeteam.network.model.response.ListResponse
import retrofit2.http.*

interface DocumentService {

  @GET("document")
  suspend fun fetchDocuments(
    @Query("session") token: String,
    @Query("pageSize") pageSize: Int,
    @Query("first") first: Int
  ): ListResponse<DocumentDto>

  @POST("document/{userId}")
  suspend fun postDocument(
    @Path("userId") userId: String,
    @Query("session") token: String,
    @Body body: DocumentBody
  ): ItemResponse<Any>
}
