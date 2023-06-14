package com.jhorgi.registermenu.di

import android.content.Context
import com.jhorgi.registermenu.data.MainRepository
import com.jhorgi.registermenu.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): MainRepository {
        val apiService = ApiConfig.getApiService()

        return MainRepository.getInstance(apiService, )

    }
}