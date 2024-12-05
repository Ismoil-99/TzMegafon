package com.example.tzmegafon.data.remote.model

sealed class UIState<out T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Loading<T>(data: T? = null) : UIState<T>(data = data)
    class Error<T>(data: T? = null, message: String) : UIState<T>(data = data, message = message)
    class Success<T>(data: T) : UIState<T>(data = data)
}