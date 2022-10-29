package com.yasxin.githubuser.ui.main

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yasxin.githubuser.api.RetrofitClient
import com.yasxin.githubuser.data.model.User
import com.yasxin.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel() : ViewModel(), Parcelable {

    val listUsers = MutableLiveData<ArrayList<User>>()

    constructor(parcel: Parcel) : this() {
    }

    fun setSearchUsers(query : String){
        RetrofitClient.apiInstance
            .getSearchUsers(query).enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserViewModel> {
        override fun createFromParcel(parcel: Parcel): UserViewModel {
            return UserViewModel(parcel)
        }

        override fun newArray(size: Int): Array<UserViewModel?> {
            return arrayOfNulls(size)
        }
    }
}