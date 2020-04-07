package com.example.kotlin_random_chat.api

import com.example.kotlin_random_chat.api.request.MessageRequest
import com.example.kotlin_random_chat.api.response.ApiResponse
import com.example.kotlin_random_chat.api.response.SigninResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RandomChatApi {

    @POST("/api/v1/randomchat/signin")
    suspend fun signin(
        @Query("nickName") nickName: String
    ): ApiResponse<SigninResponse>

    @POST("/api/v1/randomchat/message")
    suspend fun sendMessage(
        @Body request: MessageRequest
    ): ApiResponse<Any>

    companion object {
        private val instance = ApiGenerator()
            .generate(RandomChatApi::class.java)

        suspend fun signin(nickName: String) =
            withContext(Dispatchers.IO) {
                instance.signin(nickName)
            }

        suspend fun sendMessage(message: String) =
            withContext(Dispatchers.IO) {
                instance.sendMessage(MessageRequest(message))
            }

    }
}