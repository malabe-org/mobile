package com.malaabeteam.malaabeapp.ui.main.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.common_ui.extensions.showConfirmDialog
import com.malaabeteam.common_ui.extensions.showErrorSnackbar
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.common.OnTabRefreshListener
import com.malaabeteam.malaabeapp.ui.main.MainActivity
import com.malaabeteam.malaabeapp.utilities.ImagePickerHelper
import com.malaabeteam.persistance.UserSession
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber
import javax.inject.Inject


class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_profile), OnTabRefreshListener {
  override val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

  @Inject
  lateinit var session: UserSession

  override fun onAttach(context: Context) {
    fragmentComponent().inject(this)
    super.onAttach(context)
  }



  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupLogOut(view)

    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
      errorLiveData.observe(viewLifecycleOwner, Observer { renderError(it) })
      loadProfile()
    }

    userProfilePic.onClick{openImagePicker()}
  }


  @SuppressLint("SetTextI18n")
  private fun render(model: ProfileUiModel) {
    model.run {
      notAuthorized?.let { if (it) (activity as MainActivity).openLoginActivity() }
      user?.let {
        tvFullName.text = "${it.firstname } ${it.lastname}"
        tvEmail.text = it.email
        tvPhone.text = it.phone
        tvCni.text = it.cni
      }
      pictureLoaded?.let {
        Glide.with(this@ProfileFragment)
          .load(it)
          .into(userProfilePic)
      }
    }
  }

  private fun openImagePicker() {
    ImagePickerHelper.openGalleryOnly(this)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    ImagePickerHelper.onPickResult(requestCode, resultCode, data,
      onSuccess = {
        ImagePickerHelper.openCropView(
          fragment = this@ProfileFragment,
          uri = it,
          cropShape = CropImageView.CropShape.OVAL
        )
      },
      onError = {
        requireView().showErrorSnackbar(getString(R.string.errorCouldNotPickImage))
      })

    ImagePickerHelper.onCropResult(requireContext(), requestCode, resultCode, data,
      makeCircular = true,
      onSuccess = {
        viewModel.uploadPicture(it)
      },
      onError = {
        requireView().showErrorSnackbar(getString(R.string.errorCouldNotCropImage))
      })
  }

  private fun setupLogOut(view: View) {
    var res: Boolean
    view.findViewById<AppCompatButton>(R.id.titleCustomButtonLogout).setOnClickListener {
      showConfirmDialog(
        title = getString(R.string.dialogLogoutTitle),
        message = getString(R.string.dialogLogoutMessage),
        onAccept = {

          viewModel.run{
            res = logout()
            session.logOut()
            (activity as MainActivity).openLoginActivity()
          }

        }
      )
    }
  }

  private fun renderError(messageResId: Int) {
    (requireActivity() as MainActivity)
      .mainActivitySnackbarHost
      .showErrorSnackbar(getString(messageResId))
  }

  override fun onTabRefresh() {
    viewModel.loadProfile()
    childFragmentManager.fragments.forEach { fragment ->
      (fragment as? OnTabRefreshListener)?.onTabRefresh()
    }
  }
}
