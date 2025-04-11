package com.simple.imagegenerator.ui.compose

import android.Manifest
import android.os.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.simple.imagegenerator.viewmodels.ImageViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequest(imageViewModel: ImageViewModel) {
    val permissionState = rememberMultiplePermissionsState(
        buildList {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    )

    when {
        permissionState.allPermissionsGranted -> {
            imageViewModel.onPermissionGranted()
        }

        else -> {
            Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
                Text("Grant storage permission to continue")
            }
        }
    }
}