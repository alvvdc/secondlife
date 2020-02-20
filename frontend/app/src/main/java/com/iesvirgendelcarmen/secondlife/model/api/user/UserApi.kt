package com.iesvirgendelcarmen.secondlife.model.api.user

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Token
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserContact
import com.iesvirgendelcarmen.secondlife.model.UserWithoutId
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @GET(APIConfig.USER_ROUTE)
    fun getAllUsers() :Call<List<User>>

    @GET("${APIConfig.USER_ROUTE}/{id}")
    fun getUser(@Path("id") id: String,
                @Header("x-access-token") tokenHeader: String
    ): Call<User>

    @PUT("${APIConfig.USER_ROUTE}/{id}")
    fun editUser(@Header("x-access-token") tokenHeader: String,
                 @Path("id") id: String,
                 @Body user: User
    ): Call<User>

    @POST("register")
    fun register(@Body user: UserWithoutId): Call<User>

    @POST("login")
    fun login(@Body user: User): Call<Token>

    @DELETE("${APIConfig.USER_ROUTE}/{id}")
    fun deleteUser(@Header("x-access-token") tokenHeader: String,
                   @Path("id")id: String
    ): Call<Void>

    @GET("${APIConfig.USER_ROUTE}/{id}/contact")
    fun getUserContact(@Path("id") id :String, @Header("x-access-token") tokenHeader :String) :Call<UserContact>
}