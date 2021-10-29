package com.malaabeteam.persistance

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("ApplySharedPref")
abstract class BaseStorage(private val context: Context) {

  abstract val storageName: String

  private fun getSessionStorage() = context.getSharedPreferences("com.malaabeteam.malaabeapp.$storageName", Context.MODE_PRIVATE)

  fun getString(key: String) = getSessionStorage().getString(key, null)

  fun putString(key: String, value: String) {
    getSessionStorage().edit().apply {
      putString(key, value)
    }.commit()
  }

  fun getLong(key: String) = getSessionStorage().getLong(key, 0)

  fun putLong(key: String, value: Long) {
    getSessionStorage().edit().apply {
      putLong(key, value)
    }.commit()
  }

  fun getBoolean(key: String) = getSessionStorage().getBoolean(key, false)

  fun putBoolean(key: String, value: Boolean) {
    getSessionStorage().edit().apply {
      putBoolean(key, value)
    }.commit()
  }

  fun removeKeys(vararg keys: String) {
    getSessionStorage().edit().apply {
      keys.forEach { remove(it) }
    }.commit()
  }
}
