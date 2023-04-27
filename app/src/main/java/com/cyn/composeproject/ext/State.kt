package com.cyn.composeproject.ext

sealed class State {
    object Loading : State()
    object  Success : State()
    class Error(val errorMessage: String?) : State()

    fun isLoading() = this is Loading
    fun isError() = this is Error
}