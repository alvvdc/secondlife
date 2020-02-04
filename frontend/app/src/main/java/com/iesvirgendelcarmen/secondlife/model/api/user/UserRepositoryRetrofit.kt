package com.iesvirgendelcarmen.secondlife.model.api.user

import android.util.Log
import com.google.gson.JsonObject
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Token
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserWithoutId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRepositoryRetrofit: UserRepositoryDataSource {

    private var api : UserApi

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("${APIConfig.BASE_URL}/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(UserApi::class.java)
    }

    override fun getAllUsers(callback: UserRepositoryCallback.UsersCallback) {

        val call = api.getAllUsers()
        call.enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                val userResponse = response.body().orEmpty()
                callback.onResponse(userResponse)
            }
        })
    }

    override fun getUser(id: Int, callback: UserRepositoryCallback.UserCallback) {

        val call = api.getUser(id)
        call.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val userResponse = response.body()!!
                callback.onResponse(userResponse)
            }

        })
    }

    override fun editUser(user: User, callback: UserRepositoryCallback.UserCallback) {

        val call = api.editUser(
            user.id,
            user.nickname,
            user.lastName1,
            user.lastName2,
            user.email,
            user.password,
            user.phone,
            user.type
        )
        call.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                callback.onResponse(user)
            }
        })
    }

    override fun register(user: UserWithoutId, callback: UserRepositoryCallback.UserCallback) {

        val call = api.register(user)
        call.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    val userResponse = response.body() ?: User(0,"","","","","","","",0)
                    callback.onResponse(userResponse)
                } else {
                    callback.onError(response.message())
                }
            }
        })
    }

    override fun login(user: User,  callback: UserRepositoryCallback.TokenCallback) {

        val call = api.login(user)
        call.enqueue(object : Callback<Token>{
            override fun onFailure(call: Call<Token>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful){
                    val tokenResponse = response.body()
                    callback.onResponse(tokenResponse!!.token)
                } else {
                    callback.onError(response.message())
                }
            }
        })
    }

    override fun deleteUser(user: User, callback: UserRepositoryCallback.UserCallback) {

        val call = api.deleteUser(user.id)
        call.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback.onResponse(user)
            }
        })
    }



}