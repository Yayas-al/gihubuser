package com.yasxin.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.yasxin.githubuser.R
import com.yasxin.githubuser.data.model.User
import com.yasxin.githubuser.databinding.ActivityMainBinding

import com.yasxin.githubuser.ui.detail.DetailUser
import com.yasxin.githubuser.ui.favorite.FavoriteActivity
import com.yasxin.githubuser.ui.setings.PrefHelper
import com.yasxin.githubuser.ui.setings.SetingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private val pref by lazy { PrefHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUser.EXTRA_ID, data.id)
                    it.putExtra(DetailUser.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        when (pref.getBoolean("pref_is_dark_mode")){
            true ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserViewModel::class.java]
        setContentView(binding.root)
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter





            inputQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false


            }

        }

        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }


    }


    private fun searchUser() {

        binding.apply {
            val query = inputQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
               val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setings -> {
                val intent = Intent(this@MainActivity, SetingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

