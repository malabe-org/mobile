package com.malaabeteam.malaabeapp.ui.login.presentation

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_presentation.*


class PresentationFragment : Fragment(R.layout.fragment_presentation) {

  private val handler = Handler()
  private val adapter = PresentationAdapter()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView()
    setupAnimation()
  }

  override fun onDestroyView() {
    handler.removeCallbacksAndMessages(null)
    super.onDestroyView()
  }

  private fun setupView() {
    presentationLogoTitle.text = HtmlCompat.fromHtml(getString(R.string.tutorialLogoText), HtmlCompat.FROM_HTML_MODE_LEGACY)
    presentationViewPager.adapter = this.adapter
    presentationDotsIndicator.setViewPager2(presentationViewPager)

    presentationSignIn.setOnClickListener { findNavController().navigate(R.id.actionPresentationToSignIn) }
    presentationSignUp.setOnClickListener { findNavController().navigate(R.id.actionPresentationToSignUp) }

    presentationFooter.text = HtmlCompat.fromHtml(getString(R.string.tutorialFooterText), HtmlCompat.FROM_HTML_MODE_LEGACY)
    presentationFooter.setOnClickListener { (activity as LoginActivity).openMainActivity() }
  }

  private fun setupAnimation() {
    handler.postDelayed({
      val currentPage = presentationViewPager.currentItem
      val nextPage =
        if (currentPage == adapter.itemCount - 1) 0
        else currentPage + 1
      presentationViewPager.setCurrentItem(nextPage, true)
      setupAnimation()
    }, ANIMATION_DELAY_MS)
  }


    companion object {
      private const val ANIMATION_DELAY_MS = 5000L
    }
}
