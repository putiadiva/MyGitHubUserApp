package com.example.githubuserapp.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
Saran submission awal:
onFailure better pakai toast / snackbar daripada log.
 */
class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME = "adiva"
    }

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getGithubUsers(USERNAME)
    }

    fun getGithubUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object : Callback<com.example.githubuserapp.data.remote.response.Response> {
            override fun onResponse(
                call: Call<com.example.githubuserapp.data.remote.response.Response>,
                response: Response<com.example.githubuserapp.data.remote.response.Response>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<com.example.githubuserapp.data.remote.response.Response>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}