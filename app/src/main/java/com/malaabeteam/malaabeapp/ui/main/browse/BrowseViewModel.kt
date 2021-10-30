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

  fun loadDocuments(page: Int = 1) {
    if (repository.allLoaded && page > 1) return listDocuments(page)

  }

  private fun listDocuments(page: Int = 1) {
    viewModelScope.launch {
      try {
        uiState = BrowseUiModel(isLoading = true, documents = if (page == 1) emptyList() else null, hasFilters = false)

        val documents = repository
          .loadDocuments(page)
          .map { DocumentListItem(it) }

        uiState = BrowseUiModel(isLoading = false, documents = documents)
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
