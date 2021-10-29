package com.malaabeteam.common_ui.extensions

import android.app.Activity
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.malaabeteam.common_ui.R

fun Fragment.showConfirmDialog(
    title: String,
    message: String,
    onAccept: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    @StringRes positiveButtonResId: Int? = null,
    @StringRes negativeButtonResId: Int? = null
) {
    MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
        .apply {
            background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_dialog_white)
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButtonResId ?: R.string.dialogYes) { _, _ -> onAccept?.invoke() }
            setNegativeButton(negativeButtonResId ?: R.string.dialogNo) { _, _ -> onCancel?.invoke() }
            show()
        }
}
fun Activity.showInfoDialog(
  title: String,
  message: String,
  onOk: (() -> Unit)? = null,
  @StringRes positiveButtonResId: Int? = null,
  @StringRes negativeButtonResId: Int? = null
) {
  MaterialAlertDialogBuilder(this, R.style.AlertDialog)
    .apply {
      background = ContextCompat.getDrawable(context, R.drawable.bg_dialog_white)
      setTitle(title)
      setMessage(message)
      setPositiveButton(positiveButtonResId ?: R.string.textOK) { _, _ -> onOk?.invoke() }
      show()
    }
}
