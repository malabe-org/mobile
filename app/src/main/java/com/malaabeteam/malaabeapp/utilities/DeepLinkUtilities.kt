package com.malaabeteam.malaabeapp.utilities

import android.net.Uri
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.malaabeteam.malaabeapp.Config
import com.malaabeteam.malaabeapp.data.model.Document

object DeepLinkUtilities {
  fun buildLinkForDocument(doc: Document){
    val link = Firebase.dynamicLinks.dynamicLink {
      link = Uri.parse(Config.MALAABE_BASE_URL)
    }
  }
}
