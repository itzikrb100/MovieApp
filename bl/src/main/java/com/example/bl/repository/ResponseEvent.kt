package com.example.bl.repository

interface ResponseEvent {
    fun <T> onResponse(response: T, codeStatus: Int)
}