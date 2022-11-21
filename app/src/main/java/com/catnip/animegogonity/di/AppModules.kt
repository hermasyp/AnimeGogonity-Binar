package com.catnip.animegogonity.di

import com.catnip.animegogonity.data.Repository
import com.catnip.animegogonity.data.RepositoryImpl
import com.catnip.animegogonity.data.network.api.datasource.GogoAnimeApiDataSource
import com.catnip.animegogonity.data.network.api.datasource.GogoAnimeApiDataSourceImpl
import com.catnip.animegogonity.data.network.api.service.GogoAnimeApiService
import com.catnip.animegogonity.presentation.adapter.HomeAdapter
import com.catnip.animegogonity.presentation.ui.detail.AnimeDetailViewModel
import com.catnip.animegogonity.presentation.ui.home.HomeViewModel
import com.catnip.animegogonity.presentation.ui.webdetail.WebDetailViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object AppModules {

    fun getModules(): List<Module> = listOf(
        networkModule, dataSource, repository, viewModels, common, adapter
    )

    private val networkModule = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() } // singleton
        single { GogoAnimeApiService.invoke(get()) }
    }

    private val dataSource = module {
        single<GogoAnimeApiDataSource> { GogoAnimeApiDataSourceImpl(get()) } // singleton
    }

    private val repository = module {
        single<Repository> { RepositoryImpl(get()) } // singleton
    }

    private val viewModels = module {
        //Cara lain :  viewModelOf(::HomeViewModel)
        viewModel { HomeViewModel(get()) }
        viewModel { params -> WebDetailViewModel(params.get()) }
        viewModel { params -> AnimeDetailViewModel(get(),params.get()) }
    }

    private val adapter = module {
        factory { HomeAdapter() }
    }

    private val common = module {
        single { Gson() }
    }

}