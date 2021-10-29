package com.malaabeteam.common_ui.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.malaabeteam.common_ui.R
import com.malaabeteam.common_ui.utilities.SafeOnClickListener

fun View.onClick(safe: Boolean = true, action: (View) -> Unit) = setOnClickListener(SafeOnClickListener(safe, action))

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.gone() {
  visibility = View.GONE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun View.visibleIf(condition: Boolean, gone: Boolean = true) = when {
  condition -> visible()
  else -> if (gone) gone() else invisible()
}

fun View.fadeIn(duration: Long = 200) {
  visible()
  alpha = 0F
  animate().alpha(1F).setDuration(duration).start()
}

fun View.fadeOut(duration: Long = 200) {
  animate().alpha(0F).setDuration(duration).withEndAction { gone() }.start()
}

fun View.showKeyboard() {
  val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  requestFocus()
  inputMethodManager.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
  (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
    hideSoftInputFromWindow(windowToken, 0)
  }
}

fun View.showSnackbar(
  message: String,
  actionText: Int = R.string.textOK,
  backgroundRes: Int = R.drawable.bg_snackbar_info,
  length: Int = Snackbar.LENGTH_LONG,
  action: (() -> Unit)? = null
) {
  Snackbar.make(this, message, length).apply {
    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.let {
      it.maxLines = 5
    }
    view.setBackgroundResource(backgroundRes)
    setTextColor(Color.WHITE)
    setActionTextColor(Color.WHITE)
    if (action != null) {
      setAction(actionText) {
        dismiss()
        action()
      }
    }
    show()
  }
}

fun View.showErrorSnackbar(message: String, actionText: Int = R.string.textOK, action: () -> Unit = {}) {
  showSnackbar(message, actionText, R.drawable.bg_snackbar_info, Snackbar.LENGTH_INDEFINITE, action)
}

fun View.showInfoSnackbar(message: String, actionText: Int = R.string.textOK) {
  showSnackbar(message, actionText, R.drawable.bg_snackbar_info, Snackbar.LENGTH_SHORT)
}

fun screenWidth() = Resources.getSystem().displayMetrics.widthPixels

fun screenHeight() = Resources.getSystem().displayMetrics.heightPixels
