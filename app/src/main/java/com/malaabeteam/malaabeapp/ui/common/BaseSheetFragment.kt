package com.malaabeteam.malaabeapp.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.di.DaggerViewModelFactory
import javax.inject.Inject

abstract class BaseSheetFragment<T : BaseViewModel<out UiModel>> : BottomSheetDialogFragment() {

  @Inject lateinit var viewModelFactory: DaggerViewModelFactory
  protected abstract val viewModel: T

  protected abstract val layoutResId: Int

  override fun getTheme(): Int = R.style.BottomSheetDialogTheme

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)
    dialog.setOnShowListener {
      val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
      BottomSheetBehavior.from(bottomSheet).apply {
        state = BottomSheetBehavior.STATE_EXPANDED
        skipCollapsed = false
      }
    }
    return dialog
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = inflater.inflate(layoutResId, container, false)
}
