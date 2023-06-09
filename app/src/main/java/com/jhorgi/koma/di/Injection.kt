package com.jhorgi.koma.di

import android.content.Context
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.local.BookmarkRoomDatabase
import com.jhorgi.koma.data.remote.retrofit.ApiConfig
import com.jhorgi.koma.data.remote.retrofit.ApiService

object Injection {
    fun provideRepository(context: Context): MainRepository {

//        val dao =database.favoriteUserDao()
        val database = BookmarkRoomDatabase.getDatabase(context)
        val dao = database.bookmarkDao()
        val apiService = ApiConfig.getApiService()
        val apiSefvicePredict = ApiConfig.getApiServicePredict()

        return MainRepository(dao,apiService, apiSefvicePredict)

    }
}