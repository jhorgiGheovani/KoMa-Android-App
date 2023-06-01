package com.jhorgi.koma.di

import com.jhorgi.koma.data.MainRepository

object Injection {
    fun provideRepository(): MainRepository {
        return MainRepository.getInstance()
    }
}