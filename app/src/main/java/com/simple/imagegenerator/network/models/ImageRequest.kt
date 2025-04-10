package com.simple.imagegenerator.network.models

class ImageRequest(
    override val model: String = "dall-e-3",
    override val prompt: String,
    imageSize: ImageSize
) : OpenAiRequest {
    override val n: Int = 1
    override val size: String = imageSize.imageSize
}