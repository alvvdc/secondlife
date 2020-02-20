package com.iesvirgendelcarmen.secondlife.model.api.user


import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserWithoutId
import retrofit2.Call

interface UserRepositoryDataSource {
    fun getAllUsers(token: String, callback : UserRepositoryCallback.UsersCallback)
    fun getUser(id: String, token: String, callback: UserRepositoryCallback.UserCallback)
    fun editUser(user: User, token: String, callback: UserRepositoryCallback.UserCallback)
    fun register(user: UserWithoutId, callback: UserRepositoryCallback.UserCallback)
    fun login(user: User,  callback: UserRepositoryCallback.TokenCallback)
    fun deleteUser(int: String, token: String, callback: UserRepositoryCallback.DeleteCallback)
    fun getUserContactInfo(id :String, token :String, callback: UserRepositoryCallback.UserContactCallback)
}