package com.jhorgi.koma.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [BookmarkList::class], version = 1)
abstract class BookmarkRoomDatabase: RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarkRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): BookmarkRoomDatabase {
            if(INSTANCE == null){
                synchronized(BookmarkRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        BookmarkRoomDatabase::class.java, "bookmark_database")
                        .build()
                }
            }
            return  INSTANCE as BookmarkRoomDatabase
        }
    }
}