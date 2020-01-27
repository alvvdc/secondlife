package com.iesvirgendelcarmen.secondlife.model.api.user

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRepositoryRetrofit: UserRepositoryDataSource {

    private lateinit var api : UserApi

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(APIConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(UserApi::class.java)
    }

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