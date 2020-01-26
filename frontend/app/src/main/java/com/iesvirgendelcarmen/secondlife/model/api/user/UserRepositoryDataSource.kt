package com.iesvirgendelcarmen.secondlife.model.api.user

import com.iesvirgendelcarmen.secondlife.model.User
import retrofit2.Call

interface UserRepositoryDataSource {
    fun getAllUsers(callback : UserRepositoryCallback.User)
    fun getUser(id: Int): Call<User>
    fun editUser(user: User): Call<User>
    fun register(user: User): Call<User>
    fun login(user: User): Call<User>
    fun deleteUser(id: Int): Call<Void>
}