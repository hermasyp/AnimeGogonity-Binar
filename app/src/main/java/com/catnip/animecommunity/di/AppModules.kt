package com.catnip.animecommunity.di

import com.catnip.animecommunity.BuildConfig
import com.catnip.animecommunity.data.firebase.FirebaseUserAuthDataSourceImpl
import com.catnip.animecommunity.data.firebase.UserAuthDataSource
import com.catnip.animecommunity.data.repository.AnimeRepository
import com.catnip.animecommunity.data.repository.AnimeRepositoryImpl
import com.catnip.animecommunity.data.network.api.datasource.GogoAnimeApiDataSource
import com.catnip.animecommunity.data.network.api.datasource.GogoAnimeApiDataSourceImpl
import com.catnip.animecommunity.data.network.api.service.GogoAnimeApiService
import com.catnip.animecommunity.data.repository.UserRepository
import com.catnip.animecommunity.data.repository.UserRepositoryImpl
import com.catnip.animecommunity.presentation.adapter.HomeAdapter
import com.catnip.animecommunity.presentation.ui.auth.AuthViewModel
import com.catnip.animecommunity.presentation.ui.detail.AnimeDetailViewModel
import com.catnip.animecommunity.presentation.ui.home.HomeViewModel
import com.catnip.animecommunity.presentation.ui.main.MainViewModel
import com.catnip.animecommunity.presentation.ui.splash.SplashViewModel
import com.catnip.animecommunity.presentation.ui.thread.ThreadViewModel
import com.catnip.animecommunity.presentation.ui.threadform.ThreadFormViewModel
import com.catnip.animecommunity.presentation.ui.webdetail.WebDetailViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
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
        networkModule, dataSource, repository, viewModels, common, adapter, firebase
    )

    private val networkModule = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() } // singleton
        single { GogoAnimeApiService.invoke(get()) }
    }

    private val dataSource = module {
        single<GogoAnimeApiDataSource> { GogoAnimeApiDataSourceImpl(get()) } // singleton
        single<UserAuthDataSource> { FirebaseUserAuthDataSourceImpl(get()) } // singleton
    }

    private val repository = module {
        single<AnimeRepository> { AnimeRepositoryImpl(get()) } // singleton
        single<UserRepository> { UserRepositoryImpl(get()) } // singleton
    }

    private val viewModels = module {
        //Cara lain :  viewModelOf(::HomeViewModel)
        viewModel { HomeViewModel(get()) }
        viewModelOf(::SplashViewModel)
        viewModelOf(::AuthViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::ThreadViewModel)
        viewModelOf(::ThreadFormViewModel)
        viewModel { params -> WebDetailViewModel(params.get()) }
        viewModel { params -> AnimeDetailViewModel(get(), params.get()) }
    }

    private val adapter = module {
        factory { HomeAdapter() }
    }

    private val common = module {
        single { Gson() }
    }

    private val firebase = module {
        single { FirebaseAuth.getInstance() }
        single { params ->
            GoogleSignIn.getClient(
                params.get(), GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(BuildConfig.FIREBASE_WEB_CLIENT_ID)
                    .requestEmail()
                    .build()
            )
        }
    }

}