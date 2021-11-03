package com.malaabeteam.malaabeapp.ui.common.behaviour

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Some extra code added because of an issue:
 * https://issuetracker.google.com/issues/66996774
 */
class ScrollableViewBehaviour : CoordinatorLayout.Behavior<View>() {

  var isEnabled = true

  override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
    return dependency is RecyclerView
  }

  override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    stopNestedScrollIfNeeded(dy, target, type)
  }

  override fun onStartNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: View,
    directTargetChild: View,
    target: View,
    axes: Int,
    type: Int
  ) = when (axes) {
    ViewCompat.SCROLL_AXIS_VERTICAL -> true
    else -> super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
  }

  override fun onNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: View,
    target: View,
    dxConsumed: Int,
    dyConsumed: Int,
    dxUnconsumed: Int,
    dyUnconsumed: Int,
    type: Int,
    consumed: IntArray
  ) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
    if (isEnabled) {
      child.translationY = (child.translationY - dyConsumed.toFloat()).coerceAtMost(0F)
    }
    stopNestedScrollIfNeeded(dyConsumed, target, type)
  }

  private fun stopNestedScrollIfNeeded(dy: Int, target: View, type: Int) {
    if (type == ViewCompat.TYPE_NON_TOUCH) {
      if (dy == 0) {
        ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH)
      }
    }
  }
}
