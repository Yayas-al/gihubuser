package com.yasxin.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yasxin.githubuser.api.RetrofitClient
import com.yasxin.githubuser.data.local.FavoriteUser
import com.yasxin.githubuser.data.local.FavoriteUserDao
import com.yasxin.githubuser.data.local.UserDatabase
import com.yasxin.githubuser.data.model.UserDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<UserDetailResponse>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }


    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetailResponse> {
        return user
    }

    fun addToFavorite(username: String?, id: Int, avatarUrl: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username.toString(),
                id,
                avatarUrl.toString()
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }
}