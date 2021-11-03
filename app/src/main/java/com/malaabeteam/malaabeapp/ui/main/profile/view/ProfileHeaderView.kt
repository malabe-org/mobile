package com.malaabeteam.malaabeapp.ui.main.profile.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.data.model.User
import com.malaabeteam.malaabeapp.utilities.extensions.dimenToPx
import kotlinx.android.synthetic.main.view_profile_header.view.*

class ProfileHeaderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
  ConstraintLayout(context, attrs, defStyleAttr) {

  private val cornerRadius by lazy { dimenToPx(R.dimen.profileHeaderImageCornerRadius) }

  init {
    inflate(context, R.layout.view_profile_header, this)
  }

  fun onHeaderClickedListener(profileHeaderImageClicked: () -> Unit) {
    profileHeaderImage.onClick { profileHeaderImageClicked() }
  }

  fun bindView(user: User) {
    profileHeaderUserName.text = user.firstname
    profileGenderIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_innovations))
    //profileHeaderCity.text = user.address?.city ?: "---"

    profileHeaderImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_placeholder))

  }

  private fun bindImage(imageUrl: String) {
    Glide.with(this)
      .load(imageUrl)
      .transform(CenterCrop(), RoundedCorners(cornerRadius))
      .placeholder(R.drawable.user_placeholder)
      .into(profileHeaderImage)
  }
}
