package com.example.kotlin_random_chat.api.response

data class SigninResponse(
    val token: String,
    val refreshToken: String,
    val nickName: String
)