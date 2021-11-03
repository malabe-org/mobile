package com.malaabeteam.malaabeapp.data.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import com.malaabeteam.malaabeapp.data.mappers.Mappers
import com.malaabeteam.malaabeapp.data.model.Document
import com.malaabeteam.malaabeapp.di.scope.AppScope
import com.malaabeteam.network.MalaabeApi
import com.malaabeteam.persistance.UserSession
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import android.provider.MediaStore

import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaType
import timber.log.Timber
import java.io.ByteArrayOutputStream
import android.media.MediaScannerConnection

import android.os.Environment
import com.beust.klaxon.Klaxon
import com.facebook.stetho.json.ObjectMapper
import com.malaabeteam.malaabeapp.Config
import com.malaabeteam.network.model.Dhub
import com.malaabeteam.network.model.DhubReq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


@AppScope
class DocumentRepository @Inject constructor(
  private val api: MalaabeApi,
  private val session: UserSession,
  private val mappers: Mappers
) {
  companion object {
    private const val PAGE_SIZE = 10
  }


  var allLoaded = false

  suspend fun LoadDhub() = api.document.fetchDhHub("Bearer ${session.checkAuthorization()}").let{
      mappers.dHub.fromNetwork(it)
    }

  suspend fun loadDocument(documentId: String) =
    mappers.document.fromNetwork(api.document.fetchDocument(documentId))


  suspend fun loadRequest(): Document{

    val result = api.document.fetchUserRequest("Bearer ${session.checkAuthorization()}").let{
      mappers.document.fromNetwork(it)
    }

    allLoaded = true

    return result
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

    return api.document.postDocument(token, userId)
  }

  @Suppress("BlockingMethodInNonBlockingContext")
  suspend fun uploadDocumentDoc(
    context: Context,
    dhub: DhubReq?,
    images: ArrayList<Bitmap>
  ) {
    val token = session.checkAuthorization()


    val files: MutableList<File> = mutableListOf<File>()
    val contst = mutableListOf<RequestBody?>()
    for(i in 0..2){

      val contentPart = saveImage(context, images[i])?.let {
        files.add(File(it.toString()))
        val f = File(it.path!!)
        f.readBytes().toRequestBody("image/jpeg".toMediaType(), 0, f.readBytes().size)
      }
      contst.add(contentPart)

      //getImageUri(context, images[i])?.let { files.add(it) }
    }
    Timber.d("-------------------FILES: $contst")


    val json = Klaxon().toJsonString({ dhub })

    val multipartBody: MultipartBody = MultipartBody.Builder()
      .setType(MultipartBody.FORM) // Header to show we are sending a Multipart Form Data
      .addPart(json.toRequestBody("application/json".toMediaType()))
      .addFormDataPart("cniFile", files[0].absolutePath, contst[0]!!) // file param
      .addFormDataPart("receiptFile", files[1].absolutePath, contst[1]!!) // file param
      .addFormDataPart("seekerPhoto", files[2].absolutePath, contst[2]!!) // file param
      .build()

    val request = Request.Builder()
      .url(Config.MALAABE_BASE_URL+"api/request/create")
      .header("Authorization", "Bearer $token")
      .post(multipartBody)
      .build()
    val client = OkHttpClient()
    withContext(Dispatchers.IO){
      client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        Timber.d("*--*-*-*-*-*-*-*-*-*-*-*-*-*%s", response.body!!.string())
      }
    }

  }

  fun saveImage(context: Context, bitmap: Bitmap): Uri? {
    var f: File? = null
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
    val wallpaperDirectory = Environment.getExternalStoragePublicDirectory(
      Environment.DIRECTORY_PICTURES + "/env_upload"
    )
    //Si on verifie si le dossier existe deja
    if (!wallpaperDirectory.exists()) {
      wallpaperDirectory.mkdirs()
    }
    val result: Boolean
    try {
      f = File(wallpaperDirectory, "profilParent" + Calendar.getInstance().getTimeInMillis().toString() + ".jpeg")
      result = f.createNewFile()
      if (result) {
        try {
          FileOutputStream(f).use({ fo ->
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(context, arrayOf(f.getPath()), arrayOf("image/jpeg"), null)
          })
        } catch (e: Exception) {

        }
        return Uri.fromFile(f)
      }
    } catch (e1: IOException) {

    }
    return null
  }



/*
  suspend fun editProduct(requestDocumentData: RequestDocumentData): DocumentDto {
    val body = mappers.document.toCreateDocumentBody(requestDocumentData)
    val token = session.checkAuthorization()
    return api.document.editProduct(requestDocumentData.productId!!, body, token)
  }*/
}
