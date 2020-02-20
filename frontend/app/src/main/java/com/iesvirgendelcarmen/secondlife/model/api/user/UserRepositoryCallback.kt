package com.iesvirgendelcarmen.secondlife.model.api.user

import com.iesvirgendelcarmen.secondlife.model.Token
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserContact

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

    interface DeleteCallback {
        fun onResponse(message: String?)
        fun onError(message: String?)
        fun onLoading()
    }

    interface TokenCallback {
        fun onResponse(token: Token)
        fun onError(message: String?)
        fun onLoading()
    }

    interface UserContactCallback {
        fun onResponse(user :UserContact)
        fun onError(message :String)
    }
}