package com.jhorgi.koma.data

import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.model.DummyDataHome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainRepository {
    private val listDataHome = mutableListOf<DataHomeList>()


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

    fun getItemById(id: String):DataHomeList{
        return listDataHome.first {
            it.item.id == id
        }
    }


    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(): MainRepository =
            instance ?: synchronized(this) {
                MainRepository().apply {
                    instance = this
                }
            }
    }
}