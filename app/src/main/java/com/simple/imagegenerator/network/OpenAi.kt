package com.simple.imagegenerator.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

object OpenAi {

    private const val BASE_URL = "https://api.openai.com/v1/"

    internal const val TEST_API_KEY = "ADD_ME"


    private val gson = GsonBuilder().create()

    private fun retrofit(): Retrofit {
        val retrofit by lazy {
            Retrofit.Builder().client(
                OkHttpClient.Builder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .readTimeout(Duration.ofSeconds(60))
                    .writeTimeout(Duration.ofSeconds(60))
                    .build()
            ).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
        }
        return retrofit
    }

    fun images(): Images {
        val images by lazy {
            retrofit().create(Images::class.java)
        }
        return images
    }

}