package com.malaabeteam.malaabeapp.ui.main.browse

import androidx.lifecycle.viewModelScope
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.repository.DocumentRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.ui.main.browse.recycler.DocumentListItem
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class BrowseViewModel @Inject constructor(
  private val repository: DocumentRepository
) : BaseViewModel<BrowseUiModel>() {

  init {
    loadDocuments()
  }

  fun loadDocuments() {
    return listDocuments()

  }

  private fun listDocuments() {
    viewModelScope.launch {
      try {
        uiState = BrowseUiModel(isLoading = true)

        val documents = repository
          .loadRequest()
          .let { it ->
            it.requests.map{
            DocumentListItem(it)
          } }

        Timber.d("/*****************/ DOCUMENTS: $documents")

        uiState = BrowseUiModel(isLoading = false, documents = documents)
        Timber.d("/*****************/ DOCUMENTS: $uiState")
      } catch (t: Throwable) {
        onError(t)
      }
    }
  }

  private fun onError(error: Throwable) {
    uiState = BrowseUiModel(isLoading = false)
    _errorLiveData.value = R.string.errorGeneral
    Timber.w(error)
  }
}
