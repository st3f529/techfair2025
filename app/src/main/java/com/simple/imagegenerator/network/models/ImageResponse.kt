package com.simple.imagegenerator.network.models

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    override val created: Long,
    override val data: List<ImageData>
) : OpenApiResponse<ImageData>

data class ImageData(
    @SerializedName("revised_prompt")
    val revisedPrompt: String,
    @SerializedName("url")
    val url: String
)