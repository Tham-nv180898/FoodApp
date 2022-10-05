package com.mobiledev98.foodapp.types

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()

    open class Error(val errorCode: Int = -1) : Result<Nothing>()

    fun isSuccess(): Boolean {
        return this is Success
    }
    fun toData(): T {
        return (this as Success).data
    }

    fun isError(): Boolean {
        return this is Error
    }
    fun toError(): Error {
        return this as Error
    }

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[errorCode=$errorCode, errorClass=${this::class.simpleName}]"
        }
    }
}