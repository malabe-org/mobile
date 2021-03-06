package com.malaabeteam.malaabeapp.ui.main.browse

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.common_ui.extensions.showErrorSnackbar
import com.malaabeteam.common_ui.extensions.visibleIf
import com.malaabeteam.malaabeapp.Config.DEFAULT_BROWSE_GRID_SPAN
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.common.OnTabRefreshListener
import com.malaabeteam.malaabeapp.ui.common.OnTabReselectedListener
import com.malaabeteam.malaabeapp.ui.main.MainActivity
import com.malaabeteam.malaabeapp.ui.main.browse.recycler.DocumentAdapter
import com.malaabeteam.malaabeapp.utilities.EndlessRecyclerViewScrollListener
import com.malaabeteam.malaabeapp.utilities.extensions.dimenToPx
import com.malaabeteam.persistance.UserSession
import timber.log.Timber
import kotlinx.android.synthetic.main.fragment_browse.*
import javax.inject.Inject

class BrowseFragment : BaseFragment<BrowseViewModel>(R.layout.fragment_browse), OnTabReselectedListener, OnTabRefreshListener {
  override val viewModel by viewModels<BrowseViewModel> { viewModelFactory }

  private lateinit var adapter: DocumentAdapter
  private lateinit var layoutManager: GridLayoutManager
  private lateinit var scrollListener: EndlessRecyclerViewScrollListener


  @Inject
  lateinit var session: UserSession

  override fun onAttach(context: Context) {
    fragmentComponent().inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    setupRecycler()
    setupSwipeRefresh()
    setupView()

    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
      errorLiveData.observe(viewLifecycleOwner, Observer { renderError(it) })
    }

  }

  override fun onTabReselected() = scrollToStart(true)

  override fun onTabRefresh() = loadData()

  private fun setupRecycler(){
    layoutManager = GridLayoutManager(context, DEFAULT_BROWSE_GRID_SPAN)
    adapter = DocumentAdapter().apply {
      itemClickListener = { Timber.d("clicked")}
    }
    setupScrollListener()
    fragmentBrowseRecycler.apply {
      adapter = this@BrowseFragment.adapter
      layoutManager = this@BrowseFragment.layoutManager
      (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
      setHasFixedSize(true)
      clearOnScrollListeners()
      addOnScrollListener(scrollListener)
    }
  }

  private fun setupScrollListener() {
    var scrollState = EndlessRecyclerViewScrollListener.State()
    if (this::scrollListener.isInitialized) {
      titleCustomButton.visibility = View.GONE
      scrollState = scrollListener.getState()
    }
    scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager, scrollState) {
      override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
        loadData(resetScroll = false)
      }
    }
  }

  private fun setupSwipeRefresh() {
    fragmentBrowseSwipeRefresh.run {
      val swipeRefreshStartOffset = dimenToPx(R.dimen.swipeRefreshStartOffset)
      val swipeRefreshEndOffset = dimenToPx(R.dimen.swipeRefreshEndOffset)
      setProgressViewOffset(false, swipeRefreshStartOffset, swipeRefreshEndOffset)
      setOnRefreshListener { loadData() }
    }
  }

  private fun setupView() {
    titleCustomButton.visibleIf(scrollListener.getState().loading)
    fragmentBrowseLogout.visibleIf(session.isAuthorized())
    fragmentBrowseLogout.onClick {
      viewModel.logout()
      session.logOut()
      (activity as MainActivity).openLoginActivity()
    }
    loadData()
  }

  private fun loadData(
    resetScroll: Boolean = true
  ) {

    if (resetScroll) {

      scrollToStart(false)
      scrollListener.reset()
      titleCustomButton.visibility = VISIBLE
    }
    viewModel.run {
      loadDocuments()
    }
  }

  private fun scrollToStart(smooth: Boolean) {
    when {
      smooth -> {
        titleCustomButton.visibility = GONE
        fragmentBrowseRecycler.smoothScrollToPosition(0)
      }
      else -> {
        titleCustomButton.visibility = VISIBLE
        fragmentBrowseRecycler.scrollToPosition(0)
      }
    }
  }

  private fun render(uiModel: BrowseUiModel) {
    uiModel.run {
      documents?.let {
        adapter.setItems(it)
        fragmentBrowseEmptyView.visibleIf(it.isEmpty() && isLoading == false)
      }
      isLoading?.let {
        fragmentBrowseSwipeRefresh.isRefreshing = it
        //blockUi(it || documents?.any { i -> i.isLoading } == true)
      }
    }
  }
  private fun renderError(errorStringRes: Int) {
    fragmentBrowseSnackHost.showErrorSnackbar(getString(errorStringRes))
  }
}
