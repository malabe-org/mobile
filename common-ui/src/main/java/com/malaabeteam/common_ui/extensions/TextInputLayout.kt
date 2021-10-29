package com.malaabeteam.common_ui.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorMessage(errorMessage: String?) {
  errorMessage?.let {
    isErrorEnabled = true
    error = it
  } ?: run {
    error = null
    isErrorEnabled = false
  }
}

fun TextInputLayout.clearErrorIfEmpty() {
  if (editText.toString().isNotEmpty()) setErrorMessage(null)
}

inline fun EditText.doOnActionDone(crossinline action: () -> Unit) {
  setOnEditorActionListener { _, id, _ ->
    if (id == EditorInfo.IME_ACTION_DONE) action()
    false
  }
}

fun EditText.clearOnActionDone() {
  doOnActionDone {
    clearFocus()
    hideKeyboard()
  }
}
