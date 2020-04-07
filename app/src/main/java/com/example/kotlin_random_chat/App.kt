package com.example.kotlin_random_chat

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
        const val API_HOST = "http://10.0.2.2"
        const val API_PORT = 8080
        const val WEBSOCKET_ENDPOINT = "ws://10.0.2.2:8080/ws/randomchat"
    }

}