package com.example.kotlin_random_chat.domain.randomchat

data class MessageModel(
    val owner: Owner,
    val nickName: String,
    val content: String
) {
    var collapseName: Boolean = false

    enum class Owner(val viewType: Int) {
        SENDER(0),
        RECEIVER(1);
    }
}