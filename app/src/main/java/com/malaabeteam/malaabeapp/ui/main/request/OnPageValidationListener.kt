package com.malaabeteam.malaabeapp.ui.main.request

import com.malaabeteam.malaabeapp.ui.main.request.pages.RequestPage


interface OnPageValidationListener {
  fun validatePage(page: RequestPage, isValid: Boolean)
}
