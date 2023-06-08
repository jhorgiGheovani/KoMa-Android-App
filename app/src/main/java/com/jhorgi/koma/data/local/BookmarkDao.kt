package com.jhorgi.koma.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bookmarkList: BookmarkList)

    @Delete
    fun delete(bookmarkList: BookmarkList)

    @Query("SELECT * from BookmarkList")
    fun getAllBookmark(): List<BookmarkList>

//    EXISTS(SELECT 1 FROM notes WHERE id = :noteId)
    @Query("SELECT EXISTS(SELECT 1 FROM BookmarkList WHERE id = :id)")
    fun isBookmarked(id: Int): Boolean
}