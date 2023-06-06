package com.jhorgi.koma.data


import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
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
import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.HttpException

class MainRepository(
    private val bookmarkDao: BookmarkDao,
    private val apiService: ApiService
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

    fun getItemById(id: Int):DataHomeList{
        return listDataHome.first {
            it.item.id == id
        }
    }

    suspend fun postPhoto(photo : MultipartBody.Part) : UiState<PostPhoto> {
        return try {
            val response = apiService.postPhoto(photo)
            UiState.Success(response)
        } catch (e : HttpException) {
            val error = e.response()?.errorBody()?.string()
            val jsonObject = JSONObject(error!!)
            val errorMessage = jsonObject.getString("message")
            UiState.Error(errorMessage)
        } catch (e : Exception) {
            UiState.Error(e.message.toString())
        }
    }

    suspend fun getRecipeById(id:Int): UiState<RecipeByIdResponse> {
        return try {
            val response = apiService.getRecipeById(id)
            UiState.Success(response)
        }catch (e : Exception) {
            UiState.Error(e.message.toString())
        }
    }
//    fun getStoryDetail(token: String, id: String): LiveData<Result<DetailStoryResponse>> =
//        liveData {
//            emit(Result.Loading)
//            try {
//                val response = apiService.getStroryDetail(token, id)
//                _resultStoryDetail.postValue(response.story)
//                emit(Result.Success(response))
//            } catch (e: Exception) {
//                emit(Result.Error(e.message.toString()))
//            }
//        }






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

    fun isBookmarked(id: Int):Boolean{
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
            apiService: ApiService
        ): MainRepository =
            instance ?: synchronized(this) {
                MainRepository(bookmarkDao, apiService).apply {
                    instance = this
                }
            }
    }
}