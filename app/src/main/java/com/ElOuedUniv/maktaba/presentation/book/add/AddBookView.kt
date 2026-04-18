package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookView(
    onBackClick: () -> Unit,
    viewModel: AddBookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Book") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.onAction(AddBookUiAction.OnTitleChange(it)) },
                label = { Text("Title") },
                isError = uiState.titleError != null,
                supportingText = {
                    uiState.titleError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.isbn,
                onValueChange = { viewModel.onAction(AddBookUiAction.OnIsbnChange(it)) },
                label = { Text("ISBN") },
                isError = uiState.isbnError != null,
                supportingText = {
                    uiState.isbnError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.nbPages,
                onValueChange = { viewModel.onAction(AddBookUiAction.OnPagesChange(it)) },
                label = { Text("Pages") },
                isError = uiState.pagesError != null,
                supportingText = {
                    uiState.pagesError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.imageUrl,
                onValueChange = {
                    viewModel.onAction(AddBookUiAction.OnImageUrlChange(it))
                },
                label = { Text("Image URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onAction(AddBookUiAction.OnAddClick) },
                enabled = uiState.isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm Add")
            }
        }
    }
}