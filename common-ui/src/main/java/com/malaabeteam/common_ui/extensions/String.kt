package com.malaabeteam.common_ui.extensions

import androidx.core.text.HtmlCompat

fun String.asHtml() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
