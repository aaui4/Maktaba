package com.ElOuedUniv.maktaba.presentation.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.hilt.navigation.compose.hiltViewModel
import com.ElOuedUniv.maktaba.R
import kotlinx.coroutines.launch

@Composable
fun OnboardingView(
    onNavigateToLibrary: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.back1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to Maktaba",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFFDCF60)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Your personal digital library.",
                color = Color(0xFFFDCF60)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                scope.launch {
                    viewModel.onCompleteOnboarding()
                    onNavigateToLibrary()
                }
            }) {
                Text("Get Started")
            }
        }
    }
}