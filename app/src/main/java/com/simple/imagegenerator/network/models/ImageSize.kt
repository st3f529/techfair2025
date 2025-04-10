package com.simple.imagegenerator.network.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.StayCurrentLandscape
import androidx.compose.material.icons.filled.StayCurrentPortrait
import androidx.compose.ui.graphics.vector.ImageVector

enum class ImageSize(
    val imageSize: String,
    val image: ImageVector,
    val contentDescription: String
) {
    SQUARE(imageSize = "1024x1024", image = Icons.Filled.CropSquare, "square"),
    PORTRAIT(imageSize = "1024x1792", image = Icons.Filled.StayCurrentPortrait, "portrait"),
    LANDSCAPE(imageSize = "1792x1024", image = Icons.Filled.StayCurrentLandscape, "landscape")
}