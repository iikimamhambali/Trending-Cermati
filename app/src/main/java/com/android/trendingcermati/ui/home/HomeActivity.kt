package com.android.trendingcermati.ui.home

import androidx.lifecycle.Observer
import com.android.trendingcermati.R
import com.android.trendingcermati.base.BaseActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class HomeActivity : BaseActivity() {

    private val viewModel by viewModel<UserViewModel>()

    private val disposable = CompositeDisposable()

    companion object {
        const val DEBOUNCE_TIME_IN_SECOND = 1L
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun loadingData(isFromSwipe: Boolean) {
        super.loadingData(isFromSwipe)
        disposable.add(
            RxTextView.textChanges(tie_search)
                .skipInitialValue()
                .debounce(DEBOUNCE_TIME_IN_SECOND, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map { it.toString() }
                .subscribe { query ->
                    viewModel.searchUsers(query)
                }
        )
    }

    override fun observeData() {
        super.observeData()
        viewModel.resources.observe(this, Observer {
            toast("Success data")
        })

        viewModel.paged.observe(this, Observer {
            toast("Success page")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
