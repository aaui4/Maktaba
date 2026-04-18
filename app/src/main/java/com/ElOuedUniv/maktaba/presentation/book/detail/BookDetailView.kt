package com.ElOuedUniv.maktaba.presentation.book.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailView(
    onBackClick: () -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.book?.title ?: "Book Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            if (uiState.isLoading) {
                CircularProgressIndicator()

            } else if (uiState.book != null) {
                val book = uiState.book!!

                Column {

                    // 📷 صورة الكتاب
                    AsyncImage(
                        model = book.imageUrl,
                        contentDescription = book.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 📖 عنوان الكتاب
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 📊 معلومات
                    Text(text = "ISBN: ${book.isbn}")
                    Text(text = "Pages: ${book.nbPages}")

                    Spacer(modifier = Modifier.height(16.dp))

                    // ✅ نسبة التقدم (بسيطة - ثابتة)
                    val progress = 0.4f   // غيريها لاحقاً من ViewModel

                    Text(
                        text = "Reading Progress: ${(progress * 100).toInt()}%"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 📈 Progress Bar
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }

            } else if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )

            } else {
                Text("Book not found")
            }
        }
    }
}