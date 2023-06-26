package com.example.githubuserapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.remote.response.DetailResponse
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
Saran dari submission awal:
name, nFollowing, nFollower, urlAvatar bisa dimasukkan ke data class Detail Response.
 */
class DetailViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _urlAvatar = MutableLiveData<String>()
    val urlAvatar: LiveData<String> = _urlAvatar

    private val _nFollowers = MutableLiveData<Int>()
    val nFollowers: LiveData<Int> = _nFollowers

    private val _nFollowing = MutableLiveData<Int>()
    val nFollowing: LiveData<Int> = _nFollowing

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    private val _isFollowersLoading = MutableLiveData<Boolean>()
    val isFollowersLoading: LiveData<Boolean> = _isFollowersLoading

    private val _isFollowingLoading = MutableLiveData<Boolean>()
    val isFollowingLoading: LiveData<Boolean> = _isFollowingLoading

    private val _isDetailLoading = MutableLiveData<Boolean>()
    val isDetailLoading: LiveData<Boolean> = _isDetailLoading


    init {

    }

    fun getDetail(username: String) {
        _isDetailLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    _isDetailLoading.value = false
                    _name.value = response.body()?.name
                    _nFollowers.value = response.body()?.followers
                    _nFollowing.value = response.body()?.following
                    _urlAvatar.value = response.body()?.avatarUrl
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isDetailLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isFollowersLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isFollowersLoading.value = false
                _listFollowers.value = response.body()
                Log.i(TAG, "ini get followers")
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isFollowersLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isFollowingLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isFollowingLoading.value = false
                _listFollowing.value = response.body()
                Log.i(TAG, "ini get following")
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isFollowingLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setUsername(username: String) {
        _username.value = username
    }
}