package com.ElOuedUniv.maktaba.presentation.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.ElOuedUniv.maktaba.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color

@Composable
fun OnboardingView(
    onNavigateToLibrary: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.back1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Maktaba",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFFDCF60)   
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your personal digital library.",
                color = Color(0xFFFDCF60)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                viewModel.onCompleteOnboarding()
                onNavigateToLibrary()
            }) {
                Text("Get Started")
            }

        }
    }
}
