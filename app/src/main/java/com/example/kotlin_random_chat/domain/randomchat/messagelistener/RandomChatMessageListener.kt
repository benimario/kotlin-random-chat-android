package com.example.kotlin_random_chat.domain.randomchat.messagelistener

interface RandomChatMessageListener {

    fun onMessage(message: Message)

    fun onMessageError(t: Throwable)

    fun onNetworkError(t: Throwable)

    fun onStart()

    fun onClosed()

}
