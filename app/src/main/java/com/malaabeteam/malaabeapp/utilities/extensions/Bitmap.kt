package com.malaabeteam.malaabeapp.utilities.extensions

import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

fun Bitmap.toMultiPartBody(): MultipartBody.Part {
  val stream = ByteArrayOutputStream()
  compress(Bitmap.CompressFormat.JPEG, 100, stream)

  val requestBody = stream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull(), 0)

  return MultipartBody.Part.createFormData("file", "file.jpg", requestBody)
}
