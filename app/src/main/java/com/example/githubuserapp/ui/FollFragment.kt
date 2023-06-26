package com.example.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.data.remote.response.ItemsItem


/*
Saran dari submission awal:
Sebaiknya pakai view binding.
 */
class FollFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
        const val TAG = "FollFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(DetailViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        Log.i(TAG, "obj view model di foll frag : ${viewModel.toString()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_foll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var position = 0
        var username = ""

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (username != "") {
            viewModel.getFollowers(username)
            viewModel.getFollowing(username)
        }

        if (position == 1) {
            val rv: RecyclerView = view.findViewById(R.id.rv_foll)
            rv.layoutManager = LinearLayoutManager(requireActivity())
            rv.setHasFixedSize(true)
            viewModel.listFollowers.observe(viewLifecycleOwner) { lst ->
                setListFollowers(lst, rv)
            }
            viewModel.isFollowersLoading.observe(viewLifecycleOwner) {
                showLoading(it, view)
            }

        } else {
            val rv: RecyclerView = view.findViewById(R.id.rv_foll)
            rv.layoutManager = LinearLayoutManager(requireActivity())
            rv.setHasFixedSize(true)
            viewModel.listFollowing.observe(viewLifecycleOwner) { lst ->
                setListFollowing(lst, rv)
            }
            viewModel.isFollowingLoading.observe(viewLifecycleOwner) {
                showLoading(it, view)
            }
        }
    }

    private fun setListFollowers(lst: List<ItemsItem>, rv: RecyclerView) {
        val adapter = UserAdapter(lst)
        rv.adapter = adapter
    }

    private fun setListFollowing(lst: List<ItemsItem>, rv: RecyclerView) {
        val adapter = UserAdapter(lst)
        rv.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean, view: View) {
        view.findViewById<ProgressBar>(R.id.progressBar).visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}