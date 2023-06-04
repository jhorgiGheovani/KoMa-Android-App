package com.jhorgi.koma.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jhorgi.koma.data.local.BookmarkDao
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.model.DummyDataHome
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainRepository(
    private val bookmarkDao: BookmarkDao
) {
    private val listDataHome = mutableListOf<DataHomeList>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init{
        if(listDataHome.isEmpty()){
            DummyDataHome.dummydata.forEach {
                listDataHome.add(DataHomeList(it,0))
            }
        }
    }

    fun getAllItem(): Flow<List<DataHomeList>> {
        return flowOf(listDataHome)
    }

    fun getAllBookmarked():Flow<List<DataHomeList>> {
        return flowOf(listDataHome)
    }

    fun getItemById(id: String):DataHomeList{
        return listDataHome.first {
            it.item.id == id
        }
    }

//    fun addBookmarkRecipe(bookmarkList: BookmarkList): LiveData<UiState<BookmarkList>> = liveData{
//        emit(UiState.Loading)
//        try {
//            bookmarkDao.insert(bookmarkList)
//            emit(UiState.Success(bookmarkList))
//        }catch (e: Exception){
//            emit(UiState.Error(e.message.toString()))
//        }
//    }



    fun addBookmark(bookmarkList: BookmarkList){
        coroutineScope.launch(Dispatchers.IO) {
            bookmarkDao.insert(bookmarkList)
        }
    }

    fun deleteBookmark(bookmarkList: BookmarkList){
        coroutineScope.launch(Dispatchers.IO) {
            bookmarkDao.delete(bookmarkList)
        }
    }

    fun isBookmarked(id: String):Boolean{
        val result = CompletableDeferred<Boolean>()

        coroutineScope.launch(Dispatchers.IO) {
            val isBookmarked = bookmarkDao.isBookmarked(id)
            result.complete(isBookmarked)
        }

        return runBlocking { result.await() }
    }

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(
            bookmarkDao: BookmarkDao
        ): MainRepository =
            instance ?: synchronized(this) {
                MainRepository(bookmarkDao).apply {
                    instance = this
                }
            }
    }
}