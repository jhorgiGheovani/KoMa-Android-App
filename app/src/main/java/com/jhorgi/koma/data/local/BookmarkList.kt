package com.jhorgi.koma.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "BookmarkList")
@Parcelize
data class BookmarkList (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

): Parcelable