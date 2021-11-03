package com.malaabeteam.malaabeapp.ui.main.browse.recycler

import com.malaabeteam.malaabeapp.data.model.Document
import com.malaabeteam.malaabeapp.ui.common.ListItem
import com.malaabeteam.network.model.RequestUser

data class DocumentListItem (
  val document: RequestUser,
  override val isLoading: Boolean = false
  ): ListItem
