package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.Config.DATE_FORMAT
import com.malaabeteam.malaabeapp.data.model.Document
import com.malaabeteam.malaabeapp.data.model.DocUser
import org.threeten.bp.LocalDateTime
import com.malaabeteam.network.model.DocResponseDto as DocumentNetwork
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DocumentMapper @Inject constructor(
  private val userMapper: UserMapper
) {
  private val dateFormatter by lazy { DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ENGLISH) }
  fun fromNetwork(document: DocumentNetwork) =
    Document(
      requests = document.requests
    )
}
