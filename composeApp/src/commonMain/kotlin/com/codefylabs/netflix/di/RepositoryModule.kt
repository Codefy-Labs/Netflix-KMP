package com.codefylabs.netflix.di

import com.codefylabs.netflix.network.NetworkRepository
import org.koin.dsl.module

val provideRepositoryModule = module {
    single<NetworkRepository> { NetworkRepository(get()) }
}