package com.iesvirgendelcarmen.secondlife.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iesvirgendelcarmen.secondlife.model.api.*
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryDataSource
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryRetrofit
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryVolley
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryDataSource
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryRetrofit
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryVolley

class UserViewModel : ViewModel() {

    val userListLiveData = MutableLiveData<Resource<List<User>>>()
    val userLiveData = MutableLiveData<Resource<User>>()
    val deleteLiveData = MutableLiveData<Resource<String>>()
    val tokenLiveData = MutableLiveData<Resource<Token>>()

    val voidUser = User("", "", "", "", "", "", "", "", 1, "")
    val voidToken = Token("", "")

    private val userRepository : UserRepositoryDataSource = UserRepositoryRetrofit

    fun getAllUsers(){
        userRepository.getAllUsers(object: UserRepositoryCallback.UsersCallback{
            override fun onResponse(users: List<User>) {
                userListLiveData.value = Resource.success(users)
            }

            override fun onError(message: String?) {
                userListLiveData.value = Resource.error(message!!, emptyList())
            }

            override fun onLoading() {
                userListLiveData.value = Resource.loading(emptyList())
            }

        })
    }

    fun getUser(id: String, token: String){
        userRepository.getUser(id, token, object: UserRepositoryCallback.UserCallback{
            override fun onResponse(user: User) {
                userLiveData.value = Resource.success(user)
            }

            override fun onError(message: String?) {
                userLiveData.value = Resource.error(message!!, voidUser)
            }

            override fun onLoading() {
                userLiveData.value = Resource.loading(voidUser)
            }
        })
    }

    fun editUser(user: User, token: String){
        userRepository.editUser(user, token, object: UserRepositoryCallback.UserCallback{
            override fun onResponse(user: User) {
                userLiveData.value = Resource.success(user)
            }

            override fun onError(message: String?) {
                userLiveData.value = Resource.error(message!!, voidUser)
            }

            override fun onLoading() {
                userLiveData.value = Resource.loading(voidUser)
            }

        })
    }

    fun deleteUser(id: String, token: String){
        userRepository.deleteUser(id, token, object: UserRepositoryCallback.DeleteCallback{
            override fun onResponse(message: String?) {
                deleteLiveData.value = Resource.success(message!!)
            }

            override fun onError(message: String?) {
                deleteLiveData.value = Resource.error(message!!, "")
            }

            override fun onLoading() {
                deleteLiveData.value = Resource.loading("")
            }

        })
    }

    fun login(user: User){
        userRepository.login(user, object: UserRepositoryCallback.TokenCallback{
            override fun onResponse(token: Token) {
                tokenLiveData.value = Resource.success(token)
            }

            override fun onError(message: String?) {
                tokenLiveData.value = Resource.error(message!!, voidToken)
            }

            override fun onLoading() {
                tokenLiveData.value = Resource.loading(voidToken)
            }
        })
    }

    fun register(user: UserWithoutId){
        userRepository.register(user, object: UserRepositoryCallback.UserCallback{
            override fun onResponse(user: User) {
                userLiveData.value = Resource.success(user)
            }

            override fun onError(message: String?) {
                userLiveData.value = Resource.error(message!!, voidUser)
            }

            override fun onLoading() {
                userLiveData.value = Resource.loading(voidUser)
            }

        })
    }

}