package com.example.githubuserapp.di

import android.content.Context
import com.example.githubuserapp.data.FavoriteUserRepository
import com.example.githubuserapp.data.local.room.FavoriteUserDatabase
import com.example.githubuserapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return FavoriteUserRepository.getInstance(dao, appExecutors)
    }
}