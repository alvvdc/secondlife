package com.iesvirgendelcarmen.secondlife.model.api.user

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.api.VolleySingleton
import retrofit2.Call

object UserRepositoryVolley: UserRepositoryDataSource {

    override fun getAllUsers(callback: UserRepositoryCallback.User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(id: Int): Call<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editUser(user: User): Call<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun register(user: User): Call<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(user: User): Call<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(id: Int): Call<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}