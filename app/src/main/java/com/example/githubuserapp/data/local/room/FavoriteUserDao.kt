package com.example.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {

    @Query("SELECT * FROM favorite_user")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT EXISTS (SELECT * FROM favorite_user WHERE username = :username)")
    fun getIsFavoriteUser(username: String): LiveData<Boolean>

    @Insert
    fun insert(favoriteUser: FavoriteUser)

    @Update
    fun update(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)
}