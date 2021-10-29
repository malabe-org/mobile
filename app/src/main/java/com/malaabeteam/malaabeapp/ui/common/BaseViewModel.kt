package com.malaabeteam.malaabeapp.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malaabeteam.malaabeapp.utilities.SingleLiveEvent

@Suppress("PropertyName")
open class BaseViewModel<UM : UiModel> : ViewModel() {
  private val _uiLiveData = MutableLiveData<UM>()
  val uiLiveData: LiveData<UM> get() = _uiLiveData

  protected val _infoLiveData = SingleLiveEvent<Int>()
  val infoLiveData: LiveData<Int> get() = _infoLiveData

  protected val _errorLiveData = SingleLiveEvent<Int>()
  val errorLiveData: LiveData<Int> get() = _errorLiveData

  @Suppress("UNCHECKED_CAST")
  protected var uiState: UM?
    get() = _uiLiveData.value
    set(value) = when (value) {
      null -> _uiLiveData.value = value!!
      else -> _uiLiveData.value = _uiLiveData.value?.update(value) as? UM ?: value
    }
}
