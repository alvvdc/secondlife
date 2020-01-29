package com.iesvirgendelcarmen.secondlife.model.api.user

import com.iesvirgendelcarmen.secondlife.model.User

interface UserRepositoryCallback {

    interface UsersCallback {
        fun onResponse(users: List<User>)
        fun onError(message: String?)
        fun onLoading()
    }

    interface UserCallback {
        fun onResponse(user: User)
        fun onError(message: String?)
        fun onLoading()
    }

    interface TokenCallback {
        fun onResponse(token: String)
        fun onError(message: String?)
        fun onLoading()
    }
}