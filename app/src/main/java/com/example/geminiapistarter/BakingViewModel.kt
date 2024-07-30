//package com.example.geminiapistarter
//
//import android.graphics.Bitmap
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.ai.client.generativeai.GenerativeModel
//import com.google.ai.client.generativeai.type.content
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class BakingViewModel : ViewModel() {
//    private val _uiState: MutableStateFlow<UiState> =
//        MutableStateFlow(UiState.Initial)
//    val uiState: StateFlow<UiState> =
//        _uiState.asStateFlow()
//
//    private val generativeModel = GenerativeModel(
//        modelName = "gemini-1.5-flash",
//        apiKey = BuildConfig.apiKey
//    )
//
//    fun sendPrompt(
//        bitmap: Bitmap,
//        prompt: String
//    ) {
//        _uiState.value = UiState.Loading
//
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = generativeModel.generateContent(
//                    content {
//                        image(bitmap)
//                        text(prompt)
//                    }
//                )
//                response.text?.let { outputContent ->
//                    _uiState.value = UiState.Success(outputContent)
//                }
//            } catch (e: Exception) {
//                _uiState.value = UiState.Error(e.localizedMessage ?: "")
//            }
//        }
//    }
//}


package com.example.geminiapistarter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class BakingViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun sendQuery(query: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text("determine which emotion  best describes the sentence among ('Fear', 'Neutral', 'Surprise', 'Disgust', 'Desire', 'Affection', 'Happiness', 'Anger', 'Sadness', 'Optimism') {$query}. Answer in one word only")
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
