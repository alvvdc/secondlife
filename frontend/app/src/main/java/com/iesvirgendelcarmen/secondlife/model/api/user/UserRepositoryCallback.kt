package com.iesvirgendelcarmen.secondlife.model.api.user


interface UserRepositoryCallback {
    interface User {
        fun onResponse(user :List<User>)
        fun onError(message :String)
        fun onLoading()
    }
}