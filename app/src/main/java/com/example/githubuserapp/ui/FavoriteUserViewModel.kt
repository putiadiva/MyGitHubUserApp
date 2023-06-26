package com.example.githubuserapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.FavoriteUserRepository
import com.example.githubuserapp.data.local.entity.FavoriteUser

class FavoriteUserViewModel(private val favoriteUserRepository: FavoriteUserRepository) : ViewModel() {

    companion object {
        private val TAG = "FavoriteUserViewModel"
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = favoriteUserRepository.getAllFavoriteUser()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        Log.i(TAG, "param: ${username}")
        return favoriteUserRepository.getFavoriteUserByUsername(username)
    }

    fun getIsFavoriteUser(username: String): LiveData<Boolean> {
        return favoriteUserRepository.getIsFavoriteUser(username)
    }

    fun addFavoriteUser(username: String, avatarUrl: String) {
        val favoriteUser = FavoriteUser(username, avatarUrl)
        favoriteUserRepository.insert(favoriteUser)
    }

//    fun deleteFavoriteUser(username: String) {
//        val favoriteUser = getFavoriteUserByUsername(username).value!!
//        favoriteUserRepository.delete(favoriteUser)
//    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        favoriteUserRepository.delete(favoriteUser)
    }
}