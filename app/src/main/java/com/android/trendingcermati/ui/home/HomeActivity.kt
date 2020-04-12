package com.android.trendingcermati.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.android.trendingcermati.R
import com.android.trendingcermati.base.BaseActivity
import com.android.trendingcermati.extention.hideKeyboard
import com.android.trendingcermati.extention.loadFromResource
import com.android.trendingcermati.ui.home.adapter.HomeAdapter
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.sectionEmptyState
import kotlinx.android.synthetic.main.layout_connection_lost.btnReload
import kotlinx.android.synthetic.main.layout_connection_lost.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class HomeActivity : BaseActivity() {

    private val viewModel by viewModel<UserViewModel>()
    private val adapterHome by lazy { HomeAdapter() }

    private val disposable = CompositeDisposable()

    companion object {
        const val DEBOUNCE_TIME_IN_SECOND = 1L
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setViewInFront()
        setupRecyclerView()
    }

    override fun initEvent() {
        super.initEvent()
        setOnClickReload()
    }

    private fun setupRecyclerView() {
        with(rvUser) {
            adapter = adapterHome
        }
    }

    private fun setViewGone() {
        sectionEmptyState.visibility = View.GONE
    }

    private fun setViewInFront() {
        ivHeader.bringToFront()
    }

    private fun setVisibilityContent(visible: Boolean) {
        when (visible) {
            true -> {
                progressView.visibility = View.GONE
//                rvUser.visibility = View.VISIBLE
            }
            else -> {
//                rvUser.visibility = View.GONE
                progressView.visibility = View.VISIBLE
            }
        }

    }

    private fun setOnClickReload() {
        btnReload.setOnClickListener { loadingData() }
    }

    override fun loadingData(isFromSwipe: Boolean) {
        super.loadingData(isFromSwipe)
        disposable.add(
            RxTextView.textChanges(tie_search)
                .skipInitialValue()
                .debounce(DEBOUNCE_TIME_IN_SECOND, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map { it.toString() }
                .subscribe { query ->
                    viewModel.searchUsers(query)
                    setViewGone()
                }
        )
    }

    override fun observeData() {
        super.observeData()
        viewModel.resources.observe(this, Observer {
            parseObserveData(it,
                resultSuccess = { result, _ ->
                    adapterHome.status = it.status
                    when (result.item.isEmpty()) {
                        true -> onDataNotFound()
                    }
                }, resultDataNotFound = {})
        })

        viewModel.paged.observe(this, Observer {
            adapterHome.submitList(it)
        })
    }

    override fun startLoading() {
        setVisibilityContent(false)
    }

    override fun stopLoading() {
        setVisibilityContent(true)
    }

    override fun onDataNotFound() {
        this.hideKeyboard(sectionEmptyState)
        sectionEmptyState.apply {
            visibility = View.VISIBLE
            ivEmpty.loadFromResource(R.drawable.ic_empty_user)
            tvTitleEmpty.text = getString(R.string.label_title_empty_data)
            tvSubtitleEmpty.text = getString(R.string.label_subtitle_empty_data)
        }
    }

    override fun onLimitExceeded() {
        this.hideKeyboard(sectionMain)
        Snackbar.make(sectionMain, "Please try again in one minute", Snackbar.LENGTH_SHORT)
//        toast("Please try again in one minute")
    }

    override fun onInternetError() {
        this.hideKeyboard(sectionEmptyState)
        sectionEmptyState.apply {
            visibility = View.VISIBLE
            ivEmpty.loadFromResource(R.drawable.ic_empty_connection)
            tvTitleEmpty.text = getString(R.string.label_title_error_connection)
            tvSubtitleEmpty.text = getString(R.string.label_subtitle_error_connection)
            btnReload.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
