package com.malaabeteam.malaabeapp.utilities.extensions

import android.widget.EditText
import androidx.core.view.forEach
import com.google.android.material.chip.ChipGroup

fun ChipGroup.enableChips(enable: Boolean) = forEach { it.isClickable = enable }

fun EditText.setCursorAtTheEnd() { setSelection(length()) }
