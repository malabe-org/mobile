package com.malaabeteam.malaabeapp.ui.main.browse.recycler

import com.malaabeteam.malaabeapp.data.model.Document
import com.malaabeteam.malaabeapp.ui.common.ListItem

data class DocumentListItem (
  val document: Document,
  override val isLoading: Boolean = false
  ): ListItem
