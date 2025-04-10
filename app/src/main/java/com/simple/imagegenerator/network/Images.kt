package com.simple.imagegenerator.network

import com.simple.imagegenerator.network.models.ImageRequest
import com.simple.imagegenerator.network.models.ImageResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Images {

    @POST("images/generations")
    suspend fun createFromPrompt(
        @Header("Authorization") auth: String = "Bearer ${OpenAi.TEST_API_KEY}",
        @Body request: ImageRequest
    ): ImageResponse

}