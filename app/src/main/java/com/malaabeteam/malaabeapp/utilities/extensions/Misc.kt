package com.malaabeteam.malaabeapp.utilities.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

inline fun RequestBuilder<Drawable>.onError(crossinline action: () -> Unit) =
  addListener(object : RequestListener<Drawable?> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
      action()
      return false
    }

    override fun onResourceReady(
      resource: Drawable?,
      model: Any?,
      target: Target<Drawable?>?,
      dataSource: DataSource?,
      isFirstResource: Boolean
    ) = false
  })

inline fun RequestBuilder<Drawable>.onSuccess(crossinline action: () -> Unit) =
  addListener(object : RequestListener<Drawable?> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean) = false

    override fun onResourceReady(
      resource: Drawable?,
      model: Any?,
      target: Target<Drawable?>?,
      dataSource: DataSource?,
      isFirstResource: Boolean
    ): Boolean {
      action()
      return false
    }
  })

inline fun RequestBuilder<Bitmap>.onBitmapSuccess(crossinline action: (Bitmap?) -> Unit) =
  addListener(object : RequestListener<Bitmap?> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap?>?, isFirstResource: Boolean) = false

    override fun onResourceReady(
      resource: Bitmap?,
      model: Any?,
      target: Target<Bitmap?>?,
      dataSource: DataSource?,
      isFirstResource: Boolean
    ): Boolean {
      action(resource)
      return false
    }
  })

inline fun RequestBuilder<Bitmap>.onBitmapError(crossinline action: () -> Unit) =
  addListener(object : RequestListener<Bitmap?> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap?>?, isFirstResource: Boolean): Boolean {
      action()
      return false
    }

    override fun onResourceReady(
      resource: Bitmap?,
      model: Any?,
      target: Target<Bitmap?>?,
      dataSource: DataSource?,
      isFirstResource: Boolean
    ) = false
  })
