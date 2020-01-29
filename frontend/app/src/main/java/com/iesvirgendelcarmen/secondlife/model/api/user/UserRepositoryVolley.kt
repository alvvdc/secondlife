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

    override fun getAllUsers(callback: UserRepositoryCallback.UsersCallback) {

    }

    override fun getUser(id: Int, callback: UserRepositoryCallback.UserCallback) {

    }

    override fun editUser(user: User, callback: UserRepositoryCallback.UserCallback) {

    }

    override fun register(user: User, callback: UserRepositoryCallback.UserCallback) {

    }

    override fun login(user: User, callback: UserRepositoryCallback.TokenCallback) {

    }

    override fun deleteUser(user: User, callback: UserRepositoryCallback.UserCallback) {

    }


}