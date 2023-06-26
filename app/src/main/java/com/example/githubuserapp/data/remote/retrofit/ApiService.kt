package com.example.githubuserapp.data.remote.retrofit

import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.data.remote.response.DetailResponse
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.data.remote.response.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/*
Saran submission awal:
Untuk alasan keamaan kredensial, hindari menyematkan sebuah TOKEN API
 ke dalam sebuah kelas, sebaiknya dipindahkan ke dalam berkas build.gradle
 */
interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
//    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getUsers(@Query("q") username: String) : Call<Response>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(@Path("username") username: String) : Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String) : Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String) : Call<List<ItemsItem>>
}