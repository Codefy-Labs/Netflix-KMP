package com.codefylabs.netflix.di

import com.codefylabs.netflix.ui.viewModels.HomeViewModel
import org.koin.dsl.module


val provideViewModelModule = module {
    factory {
        HomeViewModel(get())
    }
}