package com.example.kotlin_random_chat.domain.auth

import com.example.kotlin_random_chat.common.Prefs

object Auth {

    fun signin(token: String, refreshToken: String, nickName: String) {
        Prefs.token = token
        Prefs.refreshToken = refreshToken
        Prefs.nickName = nickName
    }

    fun signout() {
        Prefs.token = null
        Prefs.refreshToken = null
        Prefs.nickName = null
    }

    fun refreshToken(token: String) {
        Prefs.token = token
    }

}