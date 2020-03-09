package com.anthony.wu.github.client.search.user.base

enum class Status {
    SUCCESS,
    ERROR
}


data class Resource<out T>(val status: Status, val data: T?, val message: String?, val authHeader:String? =null ) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> success(data: T,authHeader:String): Resource<T> {
            return Resource(Status.SUCCESS, data, null,authHeader)
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }
    }
}