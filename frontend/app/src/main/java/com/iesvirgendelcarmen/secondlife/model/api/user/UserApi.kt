package com.iesvirgendelcarmen.secondlife.model.api.user

import com.google.gson.JsonObject
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.Token
import com.iesvirgendelcarmen.secondlife.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @GET(APIConfig.USER_ROUTE)
    fun getAllUsers() :Call<List<User>>

    @GET("${APIConfig.USER_ROUTE}/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @PUT("${APIConfig.USER_ROUTE}/{id}")
    fun editUser(@Path("id") id: Int,
                 @Field("nickname") nickname: String,
                 @Field("name") name: String,
                 @Field("lastName1") lastName1: String,
                 @Field("lastName2") lastName2: String,
                 @Field("email") email: String,
                 @Field("password") phone: String,
                 @Field("type") type: Int
                ): Call<User>

    @POST("register")
    fun register(@Body user: User): Call<User>

    @POST("login")
    fun login(@Body user: User): Call<Token>

    @DELETE("${APIConfig.USER_ROUTE}/{id}")
    fun deleteUser(@Path("id")id: Int): Call<Void>

}