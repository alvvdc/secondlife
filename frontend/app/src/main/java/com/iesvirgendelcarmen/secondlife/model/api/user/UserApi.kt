package com.iesvirgendelcarmen.secondlife.model.api.user

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @GET(APIConfig.PRODUCT_ROUTE)
    fun getAllUsers() :Call<List<User>>

    @GET(APIConfig.PRODUCT_ROUTE + "/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @PUT(APIConfig.PRODUCT_ROUTE + "/{id}")
    fun editUser(@Path("id") id: Int,
                 @Field("nickname") nickname: String,
                 @Field("lastName1") lastName1: String,
                 @Field("lastName2") lastName2: String,
                 @Field("email") email: String,
                 @Field("password") phone: String,
                 @Field("type") type: Int
                ): Call<User>

    @POST(APIConfig.PRODUCT_ROUTE + "/register")
    fun register(@Body user: User): Call<User>

    @POST(APIConfig.PRODUCT_ROUTE + "/login")
    fun login(@Body user: User): Call<User>

    @DELETE(APIConfig.PRODUCT_ROUTE + "/{id}")
    fun deleteUser(@Path("id")id: Int): Call<Void>

}