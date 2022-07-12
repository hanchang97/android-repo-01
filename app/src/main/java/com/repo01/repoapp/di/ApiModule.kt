package com.repo01.repoapp.di

import com.repo01.repoapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class loginApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class informationApi

    @Provides
    fun provideInformationBaseUrl() = BuildConfig.INFORMATION_BASE_URL

    @Provides
    fun provideLoginBaseUrl() = BuildConfig.LOGIN_BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    @informationApi
    fun provideInformationRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideInformationBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @loginApi
    fun provideLoginRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideLoginBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}