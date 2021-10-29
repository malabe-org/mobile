package com.malaabeteam.malaabeapp.ui.common

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.malaabeteam.malaabeapp.utilities.EndlessRecyclerViewScrollListener

abstract class BaseListFragment<T : BaseViewModel<out UiModel>>(@LayoutRes contentLayoutId: Int) : BaseFragment<T>(contentLayoutId) {

  protected lateinit var adapter: BaseAdapter<*>
  protected lateinit var layoutManager: LinearLayoutManager
  private lateinit var scrollListener: EndlessRecyclerViewScrollListener

  protected abstract fun getListRecycler(): RecyclerView

  protected abstract fun getListAdapter(): BaseAdapter<*>

  protected open fun onLoadNextPage(page: Int) = Unit

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecycler()
  }

  private fun setupScrollListener() {
    var scrollState = EndlessRecyclerViewScrollListener.State()
    if (this::scrollListener.isInitialized) {
      scrollState = scrollListener.getState()
    }

    scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager, scrollState) {
      override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
        onLoadNextPage(page)
      }
    }
  }

  protected fun setupRecycler() {
    adapter = getListAdapter()
    layoutManager = LinearLayoutManager(context)
    setupScrollListener()
    getListRecycler().run {
      layoutManager = this@BaseListFragment.layoutManager
      adapter = this@BaseListFragment.adapter
      setHasFixedSize(true)
      clearOnScrollListeners()
      addOnScrollListener(scrollListener)
    }
  }
}
