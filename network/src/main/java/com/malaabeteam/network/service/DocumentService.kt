package com.malaabeteam.network.service


import com.malaabeteam.network.model.DhubsDto
import com.malaabeteam.network.model.DocResponseDto
import com.malaabeteam.network.model.RequestUser
import com.malaabeteam.network.model.request.DocumentBody
import com.malaabeteam.network.model.response.ItemResponse
import com.malaabeteam.network.model.response.ListResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface DocumentService {

  @GET("product/{id}")
  suspend fun fetchDocumentDetails(@Path("id") documentId: String): DocResponseDto

  @GET("document")
  suspend fun fetchDocuments(
    @Query("session") token: String,
    @Query("pageSize") pageSize: Int,
    @Query("first") first: Int
  ): ListResponse<RequestUser>

  @GET("api/request/for_seeker")
  suspend fun fetchUserRequest(
    @Header("Authorization") token: String
  ): DocResponseDto

  @GET("api/dh_hub")
  suspend fun fetchDhHub(
    @Header("Authorization") token: String
  ): DhubsDto

  @POST("document/{_id}")
  suspend fun postDocument(
    @Path("_id") userId: String,
    @Query("session") token: String,
    @Body body: DocumentBody
  ): ItemResponse<Any>



  @Multipart
  @POST("/api/request/create")
  suspend fun uploadProductImage(
    @Part files: MultipartBody
  ): ItemResponse<DocResponseDto>
}
