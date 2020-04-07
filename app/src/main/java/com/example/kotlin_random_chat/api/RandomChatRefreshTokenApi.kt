package com.example.kotlin_random_chat.api

import com.example.kotlin_random_chat.api.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.POST
import retrofit2.http.Query

interface RandomChatRefreshTokenApi {

    @POST("/api/v1/randomchat/refresh_token")
    suspend fun refreshToken(
        @Query("grant_type") grantType: String = "refresh_token"
    ): ApiResponse<String>

    companion object {
        private val instance = ApiGenerator()
            .generateRefreshClient(RandomChatRefreshTokenApi::class.java)

        suspend fun refreshToken() =
            withContext(Dispatchers.IO) {
                instance.refreshToken()
            }
    }

}