package com.example.bl.networkRequest

import android.os.Handler

interface IApiClient<T> {
    fun createApi(): T
    fun setHandler(handlerParent: Handler) {}
}