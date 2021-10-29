package com.malaabeteam.common_ui.utilities

import android.view.View

private const val SAFE_INTERVAL = 650

class SafeOnClickListener(
    private val isSafe: Boolean,
    private val action: (view: View) -> Unit
) : View.OnClickListener {
    private var lastClickTimestamp = 0L

    override fun onClick(clickedView: View) {
        if (!isSafe) {
            action(clickedView)
            return
        }
        val currentTimestamp = System.currentTimeMillis()
        if (lastClickTimestamp == 0L || currentTimestamp - lastClickTimestamp > SAFE_INTERVAL) {
            action(clickedView)
            lastClickTimestamp = currentTimestamp
        }
    }
}