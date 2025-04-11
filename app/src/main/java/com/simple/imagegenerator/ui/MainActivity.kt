package com.simple.imagegenerator.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simple.imagegenerator.ui.compose.DisplayWallpaperSheet
import com.simple.imagegenerator.ui.compose.ImageRatioSelection
import com.simple.imagegenerator.ui.theme.ImageGeneratorTheme
import com.simple.imagegenerator.ui.compose.Loading
import com.simple.imagegenerator.ui.compose.OopsError
import com.simple.imagegenerator.ui.compose.PermissionRequest
import com.simple.imagegenerator.ui.compose.PromptInput
import com.simple.imagegenerator.viewmodels.ImageViewModel
import com.simple.imagegenerator.viewmodels.UiState

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            ImageGeneratorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            scrollBehavior = scrollBehavior,
                            title = {
                                Text(
                                    text = "Cam's Wallpaper Generator"
                                )
                            },
                            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                        )
                    },
                ) { innerPadding ->
                    WallpaperGenerator(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WallpaperGenerator(
    modifier: Modifier = Modifier,
    viewModel: ImageViewModel = viewModel()
) {

    Column(modifier = modifier.fillMaxSize()) {
        Row {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageRatioSelection(viewModel = viewModel)
                Text(text = "Select image ratio")
            }
        }
        Row {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (val state = viewModel.uiState.collectAsStateWithLifecycle().value) {
                    is UiState.Init -> {
                        PermissionRequest(imageViewModel = viewModel)
                    }
                    is UiState.Input -> {
                        PromptInput(imageViewModel = viewModel)
                    }

                    is UiState.Loading -> {
                        Loading(state.finalizingWallpaper)
                    }

                    is UiState.Success -> {
                        DisplayWallpaperSheet(
                            viewModel = viewModel,
                            imageUri = state.response.data.first().url
                        ) {
                            viewModel.reset()
                        }
                    }

                    is UiState.Error -> {
                        OopsError()
                    }
                }
            }
        }
    }
}
