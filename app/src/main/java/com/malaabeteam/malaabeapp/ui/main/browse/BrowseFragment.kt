package com.malaabeteam.malaabeapp.ui.main.browse

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.malaabeteam.common_ui.extensions.onClick
import com.malaabeteam.common_ui.extensions.showErrorSnackbar
import com.malaabeteam.common_ui.extensions.visibleIf
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import com.malaabeteam.malaabeapp.ui.main.MainActivity
import com.malaabeteam.malaabeapp.ui.main.browse.recycler.DocumentAdapter
import com.malaabeteam.malaabeapp.utilities.EndlessRecyclerViewScrollListener
import com.malaabeteam.malaabeapp.utilities.extensions.dimenToPx
import com.malaabeteam.persistance.UserSession
import timber.log.Timber
import kotlinx.android.synthetic.main.fragment_browse.*
import javax.inject.Inject

class BrowseFragment : BaseFragment<BrowseViewModel>(R.layout.fragment_browse) {
  override val viewModel by viewModels<BrowseViewModel> { viewModelFactory }

  private lateinit var adapter: DocumentAdapter
  private lateinit var layoutManager: LinearLayoutManager
  private lateinit var scrollListener: EndlessRecyclerViewScrollListener

  private var topBarTranslation = 0F

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

  override fun onDestroyView() {
    super.onDestroyView()
  }

  private fun setupRecycler(){
    layoutManager = LinearLayoutManager(context)
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
      scrollState = scrollListener.getState()
    }
    scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager, scrollState) {
      override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
        loadData(resetScroll = false, page = page)
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
    fragmentBrowseLogout.visibleIf(!session.isAuthorized())
    fragmentBrowseLogout.onClick { (activity as MainActivity).openLoginActivity() }
    loadData()
  }

  private fun loadData(
    resetScroll: Boolean = true,
    page: Int = 1
  ) {
    if (resetScroll) {
      scrollToStart(false)
      scrollListener.reset()
    }
    viewModel.run {
      loadDocuments(page)
    }
  }

  private fun scrollToStart(smooth: Boolean) {
    when {
      smooth -> fragmentBrowseRecycler.smoothScrollToPosition(0)
      else -> fragmentBrowseRecycler.scrollToPosition(0)
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

      }
    }
  }
  private fun renderError(errorStringRes: Int) {
    fragmentBrowseSnackHost.showErrorSnackbar(getString(errorStringRes))
  }
}
