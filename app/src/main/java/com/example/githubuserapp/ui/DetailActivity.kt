package com.example.githubuserapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var detailViewModel: DetailViewModel
    lateinit var favoriteUserViewModel: FavoriteUserViewModel
    private lateinit var user: ItemsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER, ItemsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER)
        } !!

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        Log.i(TAG, "obj view model di detail act : ${detailViewModel.toString()}")

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        favoriteUserViewModel = ViewModelProvider(this, factory).get(FavoriteUserViewModel::class.java)
        favoriteUserViewModel.getFavoriteUserByUsername(user!!.login).observe(this@DetailActivity) { favUser ->
            if (favUser == null) {
                Log.i(TAG, "bukan fav user")
                val btn: Button = findViewById(R.id.btn_toggle_fav)
                btn.setText("Add to favorites")
                btn.setOnClickListener {
                    Toast.makeText(this, "Susccess", Toast.LENGTH_SHORT).show()
                    favoriteUserViewModel.addFavoriteUser(user.login, user.avatarUrl)
                }
            } else {
                Log.i(TAG, "fav user")
                val btn: Button = findViewById(R.id.btn_toggle_fav)
                btn.setText("Remove from favorites")
                btn.setOnClickListener {
                    Toast.makeText(this, "Susccess", Toast.LENGTH_SHORT).show()
                    favoriteUserViewModel.deleteFavoriteUser(favUser)
                }
            }
        }

        if (user != null) {
            binding.tvUsername.text = user.login
            detailViewModel.setUsername(user.login)
            detailViewModel.getDetail(user.login)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity)
        if (user != null) {
            sectionsPagerAdapter.username = user.login
        }
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.isDetailLoading.observe(this, {
            showLoading(it)
        })

        detailViewModel.name.observe(this, { name ->
            setName(name)
        })

        detailViewModel.nFollowers.observe(this, { n ->
            setNFollowers(n)
        })

        detailViewModel.nFollowing.observe(this, { n ->
            setNFollowing(n)
        })

        detailViewModel.urlAvatar.observe(this, { url ->
            setAvatar(url)
        })

        supportActionBar?.elevation = 0f

    }

    private fun setAvatar(url: String?) {
        Glide.with(this@DetailActivity)
            .load(url)
            .into(binding.ivAvatar)
    }

    private fun setName(name: String?) {
        binding.tvName.text = name
    }

    private fun setNFollowers(n: Int?) {
        binding.tvNFollowers.text = "${n.toString()} Followers"
    }

    private fun setNFollowing(n: Any) {
        binding.tvNFollowing.text = "${n.toString()} Following"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
        const val TAG = "DetailActivity"
    }
}