package com.malaabeteam.malaabeapp.utilities

import android.content.Context
import com.malaabeteam.malaabeapp.R
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class ErrorParser @Inject constructor(private val context: Context) {
  fun getErrorMessage(e: Throwable): String = when (e) {
    is HttpException -> e.response()?.errorBody()?.string()?.let {
      try {
        JSONObject(it).getJSONObject("error").getString("message")
      } catch (e: Exception) {
        return context.getString(R.string.errorGeneral)
      }
    } ?: context.getString(R.string.errorGeneral)
    else -> context.getString(R.string.errorGeneral)
  }
}
