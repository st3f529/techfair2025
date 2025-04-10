package com.simple.imagegenerator.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simple.imagegenerator.viewmodels.ImageViewModel

@Composable
fun PromptInput(imageViewModel: ImageViewModel) {
    var prompt by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.wrapContentHeight().fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text(if(inputError) "Prompt must not be blank" else "So, what we makin'?") },
            isError = inputError
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            inputError = prompt.isBlank()
            if (!inputError) {
                imageViewModel.createForPrompt(prompt)
                inputError = false
            }
        }
        ) {
            Text(text = "Create Wallpaper", textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PromptInputPreview() {
    PromptInput(imageViewModel = viewModel())
}