package com.iesvirgendelcarmen.secondlife.model.api.user


import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserWithoutId
import retrofit2.Call

interface UserRepositoryDataSource {
    fun getAllUsers(callback : UserRepositoryCallback.UsersCallback)
    fun getUser(id: Int, callback: UserRepositoryCallback.UserCallback)
    fun editUser(user: User, callback: UserRepositoryCallback.UserCallback)
    fun register(user: UserWithoutId, callback: UserRepositoryCallback.UserCallback)
    fun login(user: User,  callback: UserRepositoryCallback.TokenCallback)
    fun deleteUser(user: User, callback: UserRepositoryCallback.UserCallback)
}