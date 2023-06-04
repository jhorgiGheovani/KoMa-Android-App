package com.jhorgi.koma.di

import android.content.Context
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.local.BookmarkRoomDatabase

object Injection {
    fun provideRepository(context: Context): MainRepository {

//        val dao =database.favoriteUserDao()
        val database = BookmarkRoomDatabase.getDatabase(context)
        val dao = database.bookmarkDao()
        return MainRepository.getInstance(dao)
    }
}