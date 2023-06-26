package com.example.githubuserapp.data

import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.local.room.FavoriteUserDao
import com.example.githubuserapp.utils.AppExecutors

class FavoriteUserRepository private constructor(
    private val favoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
){
    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            newsDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(newsDao, appExecutors)
            }.also { instance = it }
    }

    fun insert(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute { favoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute { favoriteUserDao.delete(favoriteUser) }
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = favoriteUserDao.getAllFavoriteUser()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = favoriteUserDao.getFavoriteUserByUsername(username)

    fun getIsFavoriteUser(username: String): LiveData<Boolean> = favoriteUserDao.getIsFavoriteUser(username)
}