package com.iesvirgendelcarmen.secondlife.model.api

class Resource <T>(val status :Status, val data :T, val message :String?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data :T) :Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message :String, data :T) :Resource<T> {
            return Resource(Status.ERROR, data, null)
        }

        fun <T> loading(data :T) :Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}