package com.simple.imagegenerator.network.models

interface OpenApiResponse<T> {
    val created: Long
    val data: List<T>
}