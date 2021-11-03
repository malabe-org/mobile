package com.malaabeteam.malaabeapp.ui.main.request.pages

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageDescription.RequestDescriptionFragment
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageDocuments.RequestDocumentsFragment
import com.malaabeteam.malaabeapp.ui.main.request.pages.pageEnd.RequestEndFragment

class RequestPagesAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  override fun getItemCount() = RequestPage.values().size

  @NonNull
  override fun createFragment(position: Int) = when (position) {
    RequestPage.DESCRIPTION.index -> RequestDescriptionFragment.create()
    RequestPage.DOCUMENTS.index -> RequestDocumentsFragment.create()
    RequestPage.END.index -> RequestEndFragment.create()
    else -> error("Invalid position!")
  }
}
