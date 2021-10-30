package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.Config.DATE_FORMAT
import com.malaabeteam.malaabeapp.data.model.Document
import com.malaabeteam.malaabeapp.data.model.Image
import org.threeten.bp.LocalDateTime
import com.malaabeteam.network.model.DocumentDto as DocumentNetwork
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DocumentMapper @Inject constructor(
  private val userMapper: UserMapper
) {
  private val dateFormatter by lazy { DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ENGLISH) }
  fun fromNetwork(document: DocumentNetwork) =
    Document(
      id = document.id,
      title = document.title,
      typeDocument = document.typeDocument,
      imageUrl = document.imageUrl,
      created = document.created,
      captures = document.captures?.map {
        Image(
          it.imageId,
          it.imageUrl ?: "",
          it.description ?: "",
          LocalDateTime.parse(it.created, dateFormatter),
          it.isBlocked ?: false
        )
      } ?: emptyList(),
      description = document.description,
      state = document.state
    )
}
