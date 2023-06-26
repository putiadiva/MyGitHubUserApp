package com.example.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        favoriteUserViewModel = ViewModelProvider(this, factory).get(FavoriteUserViewModel::class.java)

        favoriteUserViewModel.getAllFavoriteUser().observe(this, { lst ->
            setList(lst)
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
    }

    private fun setList(lst: List<FavoriteUser>?) {
        val items = arrayListOf<ItemsItem>()
        lst?.map {
            val item = ItemsItem(it.username, it.avatarUrl!!)
            items.add(item)
        }
        binding.rvUser.adapter = UserAdapter(items)
    }
}