package com.jhorgi.koma.data


import com.jhorgi.koma.data.local.BookmarkDao
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.data.remote.response.RecipeByIngredientsResponse
import com.jhorgi.koma.data.remote.retrofit.ApiService
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.model.DummyDataHome
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody

class MainRepository(
    private val bookmarkDao: BookmarkDao,
    private val apiService: ApiService,
    private val apiServicePredict: ApiService,
) {
    private val listDataHome = mutableListOf<DataHomeList>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    init {
        if (listDataHome.isEmpty()) {
            DummyDataHome.dummydata.forEach {
                listDataHome.add(DataHomeList(it, 0))
            }
        }
    }

    fun getAllItem(): Flow<List<DataHomeList>> {
        return flowOf(listDataHome)
    }

    //===========================================================================
    suspend fun postPhoto(photo: MultipartBody.Part): PostPhoto {
        val response = apiServicePredict.postPhoto(photo)
        return response
    }


//suspend fun postPhoto(photo : MultipartBody.Part) : PostPhoto {
//    return apiServicePredict.postPhoto(photo)
//}

    //===========================================================================
    suspend fun getRecipeById(id: Int): UiState<RecipeByIdResponse> {
        return try {
            val response = apiService.getRecipeById(id)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message.toString())
        }
    }
    suspend fun getRecipeByIngredient(ingredient : String): UiState<RecipeByIngredientsResponse> {
        return try {
            val response = apiService.getRecipeByIngredient(ingredient)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message.toString())
        }
    }


    suspend fun getRecipeById2(id: Int): RecipeByIdResponse {
        return apiService.getRecipeById(id)
    }

    fun getAllBookmark(): List<BookmarkList> {
        val result = CompletableDeferred<List<BookmarkList>>()

        coroutineScope.launch(Dispatchers.IO) {
            val getValue = bookmarkDao.getAllBookmark()
            result.complete(getValue)
        }
        return runBlocking { result.await() }
    }


    fun addBookmark(bookmarkList: BookmarkList) {
        coroutineScope.launch(Dispatchers.IO) {
            bookmarkDao.insert(bookmarkList)
        }
    }

    fun deleteBookmark(bookmarkList: BookmarkList) {
        coroutineScope.launch(Dispatchers.IO) {
            bookmarkDao.delete(bookmarkList)
        }
    }

    fun isBookmarked(id: Int): Boolean {
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
            bookmarkDao: BookmarkDao,
            apiService: ApiService,
            apiServicePredict: ApiService
        ): MainRepository =
            instance ?: synchronized(this) {
                MainRepository(bookmarkDao, apiService, apiServicePredict).apply {
                    instance = this
                }
            }
    }
}