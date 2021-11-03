package com.malaabeteam.malaabeapp.ui.main.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.malaabeteam.malaabeapp.R
import com.malaabeteam.malaabeapp.ui.common.BaseFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.malaabeteam.common_ui.extensions.showErrorSnackbar
import com.malaabeteam.malaabeapp.Config
import com.malaabeteam.malaabeapp.fragmentComponent
import com.malaabeteam.malaabeapp.ui.main.MainActivity
import com.malaabeteam.malaabeapp.ui.main.browse.BrowseUiModel
import com.malaabeteam.malaabeapp.ui.main.browse.recycler.DocumentAdapter
import com.malaabeteam.malaabeapp.ui.main.notifications.recycler.NotificationAdapter
import com.malaabeteam.malaabeapp.ui.main.notifications.recycler.NotificationListItem
import com.malaabeteam.network.model.NotificationDto
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notification.*


class NotificationFragment : BaseFragment<NotificationViewModel>(R.layout.fragment_notification) {

  override val viewModel by viewModels<NotificationViewModel> {viewModelFactory}
  private lateinit var adapter: NotificationAdapter
  private lateinit var layoutManager: GridLayoutManager

  override fun onCreate(savedInstanceState: Bundle?) {
    fragmentComponent().inject(this)
    super.onCreate(savedInstanceState)
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupRecycler()

    viewModel.run {
      uiLiveData.observe(viewLifecycleOwner, Observer { render(it) })
      errorLiveData.observe(viewLifecycleOwner, Observer { renderError(it) })
    }
  }

  private fun render(uiModel: NotificationUiModel) {
    uiModel.run {
      notifications?.let {
        adapter.setItems(it)
        //fragmentBrowseEmptyView.visibleIf(it.isEmpty() && isLoading == false)
      }
      isLoading?.let {
        //fragmentBrowseSwipeRefresh.isRefreshing = it
        //blockUi(it || documents?.any { i -> i.isLoading } == true)
      }
    }
  }

  private fun setupRecycler(){
    layoutManager = GridLayoutManager(context, Config.DEFAULT_BROWSE_GRID_SPAN)
    adapter = NotificationAdapter(this.requireContext(), null).apply {
     // itemClickListener = { Timber.d("clicked")}
    }
    //setupScrollListener()
    rcVnotifs.apply {
      adapter = this@NotificationFragment.adapter
      layoutManager = this@NotificationFragment.layoutManager
      (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
      //setHasFixedSize(true)
      //clearOnScrollListeners()
      //addOnScrollListener(scrollListener)
    }
  }

  private fun renderError(messageResId: Int) {
    (requireActivity() as MainActivity)
      .mainActivitySnackbarHost
      .showErrorSnackbar(getString(messageResId))
  }
  fun loadFakeData(){
    val listOfNotifs: MutableList<NotificationListItem> = mutableListOf()

  }
  companion object {

  }
}
