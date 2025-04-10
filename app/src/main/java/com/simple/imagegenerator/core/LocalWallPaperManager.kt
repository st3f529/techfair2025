package com.simple.imagegenerator.core

import android.Manifest
import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.RequiresPermission
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LocalWallPaperManager {

    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    suspend fun setWallpaper(context: Context, imageUrl: String) {
        val manager = WallpaperManager.getInstance(context)

        withContext(Dispatchers.IO) {
            val metrics = context.resources.displayMetrics
            val screenWidth = metrics.widthPixels
            val screenHeight = metrics.heightPixels

            val loader = context.imageLoader
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .size(screenWidth, screenHeight)
                .allowHardware(false)
                .build()

            val result = loader.execute(request)
            if (result is SuccessResult) {
                val drawable = result.drawable
                val bitmap = (drawable as BitmapDrawable).bitmap
                manager.setBitmap(bitmap)
            }
        }
    }
}