package com.android.trendingcermati.dependencies.module

import com.android.trendingcermati.ui.home.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { UserViewModel(get(), get()) }
}