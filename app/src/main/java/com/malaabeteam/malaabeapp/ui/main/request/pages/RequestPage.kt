package com.malaabeteam.malaabeapp.ui.main.request.pages

enum class RequestPage(val index: Int) {
  DESCRIPTION(1),
  DOCUMENTS(0),
  END(2);

  fun isBefore(page: RequestPage) = this.index < page.index
}
