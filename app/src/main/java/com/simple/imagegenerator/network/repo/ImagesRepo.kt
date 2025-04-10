package com.simple.imagegenerator.network.repo

import com.simple.imagegenerator.network.OpenAi
import com.simple.imagegenerator.network.Images
import com.simple.imagegenerator.network.models.DallEImageRequest
import com.simple.imagegenerator.network.models.ImageResponse
import com.simple.imagegenerator.network.models.ImageSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImagesRepo(private val images: Images = OpenAi.images()) {

    suspend fun fetchImageForPrompt(
        prompt: String,
        imageSize: ImageSize
    ): Result<ImageResponse> {
        return kotlin.runCatching {
            withContext(Dispatchers.IO) {
                val result =
                    images.createFromPrompt(
                        request = DallEImageRequest(
                            prompt = prompt,
                            imageSize = imageSize
                        )
                    )
                result
            }
        }
    }
}