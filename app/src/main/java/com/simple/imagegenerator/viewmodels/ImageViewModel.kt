package com.simple.imagegenerator.viewmodels

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.imagegenerator.core.LocalWallPaperManager
import com.simple.imagegenerator.network.models.ImageResponse
import com.simple.imagegenerator.network.models.ImageSize
import com.simple.imagegenerator.network.repo.ImagesRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class UiState {
    data object Input : UiState()
    data class Loading(val finalizingWallpaper: Boolean = false) : UiState()
    data class Success(val response: ImageResponse) : UiState()
    data class Error(val error: String) : UiState()
}

class ImageViewModel(private val repository: ImagesRepo = ImagesRepo()) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Input)

    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _uiState.value
    )

    // No spamming! :)
    private var jobDebounce: Job? = null

    var imageSize: ImageSize = ImageSize.SQUARE

    fun createForPrompt(
        prompt: String
    ) {
        if (jobDebounce?.isActive == true) {
            return
        } else {
            jobDebounce = viewModelScope.launch {
                _uiState.emit(UiState.Loading())

                val result = repository.fetchImageForPrompt(prompt, imageSize)
                if (result.isSuccess) {
                    _uiState.emit(UiState.Success(result.getOrThrow()))
                } else {
                    _uiState.emit(
                        UiState.Error(
                            result.exceptionOrNull()?.toString() ?: "Darn, the request failed."
                        )
                    )
                }
            }
        }
    }

    fun saveAsWallpaper(
        context: Context, imageUri: String
    ) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading(finalizingWallpaper = true))
            LocalWallPaperManager.setWallpaper(context, imageUri)
            (context as Activity).finish()
        }
    }

    fun reset() {
        viewModelScope.launch {
            jobDebounce = null
            _uiState.emit(UiState.Input)
        }
    }

}