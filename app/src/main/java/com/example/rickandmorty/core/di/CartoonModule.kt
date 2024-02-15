package com.example.rickandmorty.core.di

import com.example.rickandmorty.BuildConfig
import com.example.rickandmorty.data.Repository
import com.example.rickandmorty.data.api.CartoonApiService
import com.example.rickandmorty.presentation.detailScreen.DetailsViewModel
import com.example.rickandmorty.presentation.mainScreen.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        provideRetrofit(get())
    }

    single {
        provideOkHttpClient(get())
    }

    single {
        provideInterceptor()
    }

    single {
        provideCartoonApiService(get())
    }
}

val repositoryModule = module {
    single { Repository(get()) }
}

val viewModelModule = module {
    viewModel {
        DetailsViewModel(get())
    }

    viewModel {
        MainViewModel(get())
    }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
}

fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder().writeTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(10L, TimeUnit.SECONDS).connectTimeout(10L, TimeUnit.SECONDS)
        .callTimeout(10L, TimeUnit.SECONDS).addInterceptor(interceptor).build()
}

fun provideInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideCartoonApiService(retrofit: Retrofit): CartoonApiService {
    return retrofit.create(CartoonApiService::class.java)
}