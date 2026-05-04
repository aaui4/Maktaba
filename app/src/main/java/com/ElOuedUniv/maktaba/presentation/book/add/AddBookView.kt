package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.navigation.NavHostController
import com.ElOuedUniv.maktaba.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookView(
    onBackClick: () -> Unit,
    onSuccessNavigate: () -> Unit,
    viewModel: AddBookViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.onAction(AddBookUiAction.OnImageSelected(it))
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onSuccessNavigate()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "ADD BOOK",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Cancel")
                    }
                },
                actions = {
                    TextButton(
                        onClick = { viewModel.onAction(AddBookUiAction.OnAddClick) },
                        enabled = uiState.isFormValid
                    ) {
                        Text("Confirm", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            val imageUri = uiState.selectedImageUri

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = { imagePicker.launch("image/*") }
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = null)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("ADD COVER IMAGE", fontWeight = FontWeight.Bold)
                    }
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator()
            }

            uiState.errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = {
                        viewModel.onAction(AddBookUiAction.OnTitleChange(it))
                    },
                    label = { Text("Book Title") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.titleError != null,
                    supportingText = {
                        uiState.titleError?.let { Text(it) }
                    }
                )

                OutlinedTextField(
                    value = uiState.isbn,
                    onValueChange = {
                        viewModel.onAction(AddBookUiAction.OnIsbnChange(it))
                    },
                    label = { Text("ISBN") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.isbnError != null,
                    supportingText = {
                        uiState.isbnError?.let { Text(it) }
                    }
                )

                OutlinedTextField(
                    value = uiState.nbPages,
                    onValueChange = {
                        viewModel.onAction(AddBookUiAction.OnPagesChange(it))
                    },
                    label = { Text("Pages") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.nbPagesError != null,
                    supportingText = {
                        uiState.nbPagesError?.let { Text(it) }
                    }
                )
            }

            Button(
                onClick = { viewModel.onAction(AddBookUiAction.OnAddClick) },
                enabled = uiState.isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Confirm")
            }

            TextButton(onClick = onBackClick) {
                Text("Cancel")
            }
        }
    }
}