package com.codefylabs.netflix.di

import com.codefylabs.netflix.ui.viewModels.HomeViewModel
import com.codefylabs.netflix.ui.viewModels.MovieDetailViewModel
import org.koin.dsl.module


val provideViewModelModule = module {
    factory { HomeViewModel(get()) }

    factory { (movieId: Long) -> MovieDetailViewModel(movieId, get()) }
}