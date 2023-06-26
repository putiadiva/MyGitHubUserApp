package com.example.githubuserapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.databinding.ItemUserBinding

class UserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val username = listUser[position].login
        val avatar = listUser[position].avatarUrl

        holder.binding.tvUsername.text = username
        Glide.with(holder.itemView.context)
            .load(avatar)
            .into(holder.binding.ivUserPic)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("extra_user", listUser[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}