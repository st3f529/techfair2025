package com.simple.imagegenerator.network.models

interface OpenAiRequest {
    val model: String
    val prompt: String
    val n: Int
    val size: String
}
