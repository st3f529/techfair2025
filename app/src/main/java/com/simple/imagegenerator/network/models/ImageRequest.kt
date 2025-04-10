package com.simple.imagegenerator.network.models

interface ImageRequest {
    val model: String
    val prompt: String
    val n: Int
    val size: String
}

class DallEImageRequest(
    override val prompt: String,
    imageSize: ImageSize
) : ImageRequest {
    override val model: String = "dall-e-3"
    override val n: Int = 1
    override val size: String = imageSize.size
}