package com.malaabeteam.malaabeapp.ui.main.request

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.repository.DocumentRepository
import com.malaabeteam.malaabeapp.ui.common.BaseViewModel
import com.malaabeteam.malaabeapp.ui.main.request.helpers.DocumentFormData
import com.malaabeteam.malaabeapp.ui.main.request.helpers.RequestDocumentData
import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage
import com.malaabeteam.malaabeapp.utilities.ErrorParser
import com.malaabeteam.persistance.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class DocumentViewModel@Inject constructor(
  private val documentRepository: DocumentRepository,
  private val userSession: UserSession,
  private val errorParser: ErrorParser
) : BaseViewModel<DocumentUiModel>()  {
  init {
      uiState = DocumentUiModel(page = RequestPage.DOCUMENTS)
  }

  private val _editLiveData = MutableLiveData<RequestDocumentData>()
  val editLiveData: LiveData<RequestDocumentData> get() = _editLiveData

  private var documentData = RequestDocumentData()


  /*fun enterEditMode(documentId: String) {
    viewModelScope.launch {
      try {
        uiState = DocumentUiModel(isLoading = true, isLoadingText = R.string.requestEditLoadingText)

        val doc = documentRepository.loadDocument(documentId)
        val description = DescriptionFormData.fromRequest(doc)

        documentData = documentData.copy(
          description = description,
          documents = doc.documents.map { DocumentFormData() }
        )

        _editLiveData.value = documentData
      } catch (e: Exception) {
        _errorLiveData.value = R.string.errorGeneral
      } finally {
        uiState = DocumentUiModel(isLoading = false)
      }
    }
  }*/

  fun nextPage() {
    uiState = when (uiState?.page) {

      RequestPage.DESCRIPTION -> DocumentUiModel(page = RequestPage.END, nextStepEnabled = true)
      RequestPage.DOCUMENTS -> DocumentUiModel(page = RequestPage.DESCRIPTION, nextStepEnabled = false)
      RequestPage.END -> DocumentUiModel(isFormCompleted = true)
      else -> error("Invalid step.")
    }
  }

  fun changePage(page: RequestPage) {
    if (page.isBefore(uiState?.page!!)) {
      uiState = DocumentUiModel(page = page)
    }
  }

  fun validatePage(page: RequestPage, isValid: Boolean) {
    if (uiState?.page != page) return
    uiState = DocumentUiModel(nextStepEnabled = isValid)
  }

  fun isRequestDataEmpty() = documentData.isEmpty()

  fun updateDocumentData(doc: List<DocumentFormData>) {
    documentData = documentData.copy(documents = doc.toList())
  }

  fun getData(context: Context): ArrayList<Uri> {
    val myList: ArrayList<Uri> = arrayListOf()
    documentData.documents
      .forEach { photo ->
        myList.add(getImageUri(context, photo.image!!)!!)
      }
    return myList
  }

  private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 85, bytes)
    val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "File", null)
    return Uri.parse(path)
  }

  fun publishProduct(context: Context, isEditMode: Boolean) {
    viewModelScope.launch {
      try {
        uiState = DocumentUiModel(isLoading = true, isLoadingText = R.string.requestDocumentProgressText, nextStepEnabled = false)
        val myList: ArrayList<Bitmap> = arrayListOf()
        documentData.documents
          .forEach { photo ->
            myList.add(photo.image!!)
          }
        withContext(Dispatchers.IO) {
          documentRepository.uploadDocumentDoc(context, myList)
        }



        uiState = DocumentUiModel(isPublished = true)
      } catch (error: Throwable) {
        uiState = DocumentUiModel(
          isLoading = false,
          nextStepEnabled = true,
          localizedError = errorParser.getErrorMessage(error)
        )
      }
    }
  }

  private suspend fun publishDocumentDocs(context: Context) {
    withContext(Dispatchers.IO) {

      try {



      } catch (error: Throwable) {
        _errorLiveData.postValue(R.string.errorCreateDocumentError)
      }
    }
  }
}
