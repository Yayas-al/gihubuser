package com.yasxin.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.yasxin.githubuser.data.local.FavoriteUser
import com.yasxin.githubuser.data.local.FavoriteUserDao
import com.yasxin.githubuser.data.local.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<MutableList<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}