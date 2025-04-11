package com.simple.imagegenerator.core

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore

class ImageSaver(
    private val applicationContext: Context,
    private val bitmap: Bitmap
){

    fun save(): Result<Unit> {
        try {
            val fileName = "wallpaper_${System.currentTimeMillis()}.jpeg"

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            val resolver = applicationContext.contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                resolver.openOutputStream(it)?.use { dest ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, dest)
                }

                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(Exception("Failed to save image"))
        }
    }

}