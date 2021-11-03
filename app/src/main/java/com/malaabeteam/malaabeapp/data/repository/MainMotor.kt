package com.malaabeteam.malaabeapp.data.repository

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.malaabeteam.malaabeapp.BuildConfig
import com.malaabeteam.malaabeapp.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

private const val TAG = "MultipartUpload"
private const val DEFAULT_TYPE = "application/octet-stream"

class MainMotor(app: Application) : AndroidViewModel(app) {
  private val ok by lazy { OkHttpClient() }
  private val _events = BroadcastChannel<String>(Channel.BUFFERED)
  val events = _events.asFlow()

  fun upload(content: MutableList<Uri>, token: String) {
    viewModelScope.launch {
      try {
        uploadMultipart(content, token)
      } catch (t: Throwable) {
        Log.e(TAG, "Exception doing upload", t)
        _events.send("BOOOOOOM!!!")
      }
    }
  }

  private suspend fun uploadMultipart(content: MutableList<Uri>, token: String) =
    withContext(Dispatchers.IO) {
      val resolver = getApplication<Application>().contentResolver
      val doc1 = DocumentFile.fromSingleUri(getApplication(), content[0])!!
      val doc2 = DocumentFile.fromSingleUri(getApplication(), content[0])!!
      val doc3 = DocumentFile.fromSingleUri(getApplication(), content[0])!!
      val type1 = (doc1.type ?: DEFAULT_TYPE).toMediaType()
      val type2 = (doc2.type ?: DEFAULT_TYPE).toMediaType()
      val type3 = (doc3.type ?: DEFAULT_TYPE).toMediaType()
      val contentPart1 = InputStreamRequestBody(type1, resolver, content[0])
      val contentPart2 = InputStreamRequestBody(type1, resolver, content[0])
      val contentPart3 = InputStreamRequestBody(type1, resolver, content[0])

      val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("token", token)
        .addFormDataPart("cniFile", doc1.name, contentPart1)
        .addFormDataPart("receipt", doc1.name, contentPart2)
        .addFormDataPart("seekerPhoto", doc1.name, contentPart3)
        .build()

      val request = Request.Builder()
        .url(Config.MALAABE_BASE_URL+"/api/request/create")
        .post(requestBody)
        .build()

      ok.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        _events.send(response.body!!.string())
      }
    }
}

