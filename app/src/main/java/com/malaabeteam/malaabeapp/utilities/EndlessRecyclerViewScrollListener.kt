package com.malaabeteam.malaabeapp.utilities

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Source:
 * https://gist.github.com/nesquena/d09dc68ff07e845cc622?ts=2
 */
abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener {

  private var currentPage = 1
  private var previousTotalItemCount = 0
  private var loading = true
  private var visibleThreshold = 5

  // Sets the starting page index
  private val startingPageIndex = 1
  private var mLayoutManager: RecyclerView.LayoutManager

  abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)

  constructor(layoutManager: LinearLayoutManager, state: State = State()) {
    mLayoutManager = layoutManager
    setState(state)
  }

  constructor(layoutManager: GridLayoutManager, state: State = State()) {
    mLayoutManager = layoutManager
    visibleThreshold *= layoutManager.spanCount
    setState(state)
  }

  private fun setState(state: State) {
    currentPage = state.currentPage
    previousTotalItemCount = state.previousTotalItemCount
    loading = state.loading
  }

  private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
    var maxSize = 0
    for (i in lastVisibleItemPositions.indices) {
      if (i == 0) {
        maxSize = lastVisibleItemPositions[i]
      } else if (lastVisibleItemPositions[i] > maxSize) {
        maxSize = lastVisibleItemPositions[i]
      }
    }
    return maxSize
  }

  override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
    var lastVisibleItemPosition = 0
    val totalItemCount = mLayoutManager.itemCount

    when (mLayoutManager) {
      is StaggeredGridLayoutManager -> {
        val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
        lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
      }
      is GridLayoutManager -> {
        lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
      }
      is LinearLayoutManager -> {
        lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
      }
    }
    if (totalItemCount < previousTotalItemCount) {
      currentPage = startingPageIndex
      previousTotalItemCount = totalItemCount
      if (totalItemCount == 0) {
        loading = true
      }
    }
    if (loading && totalItemCount > previousTotalItemCount) {
      loading = false
      previousTotalItemCount = totalItemCount
    }

    if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount && totalItemCount >= visibleThreshold) {
      currentPage++
      onLoadMore(currentPage, totalItemCount, view)
      loading = true
    }
  }

  fun reset() {
    currentPage = startingPageIndex
    previousTotalItemCount = 0
    loading = true
  }

  fun getState() = State(currentPage, previousTotalItemCount, loading)

  data class State(
    val currentPage: Int = 1,
    val previousTotalItemCount: Int = 0,
    val loading: Boolean = true
  )
}
