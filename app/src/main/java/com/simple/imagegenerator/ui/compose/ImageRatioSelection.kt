package com.simple.imagegenerator.ui.compose

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simple.imagegenerator.core.PreferenceStore.SELECTED_IMAGE_RATIO
import com.simple.imagegenerator.core.PreferenceStore.dataStore
import com.simple.imagegenerator.network.models.ImageSize
import com.simple.imagegenerator.viewmodels.ImageViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun ImageRatioSelection(
    viewModel: ImageViewModel,
    imageIds: List<ImageSize> = ImageSize.entries,
    context: Context = LocalContext.current
) {
    val scope = rememberCoroutineScope()
    val dataStore = context.dataStore

    var selectedId by remember { mutableStateOf<String?>(imageIds.first().size) }

    LaunchedEffect(Unit) {
        dataStore.data.map { prefs -> prefs[SELECTED_IMAGE_RATIO] }
            .collect { selected -> selectedId = selected }
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        imageIds.forEach { imageId ->
            val isSelected = selectedId == imageId.size

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .border(
                        width = if(isSelected) 3.dp else 1.dp,
                        color = if(isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    ).clickable {
                        viewModel.imageSize = imageId
                        selectedId = imageId.size
                        scope.launch {
                            dataStore.edit { prefs ->
                                prefs[SELECTED_IMAGE_RATIO] = imageId.size
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = rememberVectorPainter(imageId.image),
                    contentDescription = imageId.contentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageRatioSelectionPreview() {
    ImageRatioSelection(viewModel = viewModel())
}