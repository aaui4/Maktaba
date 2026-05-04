package com.ElOuedUniv.maktaba.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ElOuedUniv.maktaba.data.local.DataStoreManager
import com.ElOuedUniv.maktaba.presentation.book.BookListView
import com.ElOuedUniv.maktaba.presentation.book.add.AddBookView
import com.ElOuedUniv.maktaba.presentation.book.detail.BookDetailView
import com.ElOuedUniv.maktaba.presentation.category.CategoryListView
import com.ElOuedUniv.maktaba.presentation.onboarding.OnboardingView
import kotlinx.coroutines.flow.first

@Composable
fun NavGraph(
    dataStoreManager: DataStoreManager
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        //  SPLASH SCREEN
        composable(Screen.Splash.route) {

            var isLoading by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                val done = dataStoreManager.isOnboardingDone.first()
                isLoading = false

                if (done) {
                    navController.navigate(Screen.BookList.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                }
            }
        }

        //  ONBOARDING
        composable(Screen.Onboarding.route) {
            OnboardingView(
                onNavigateToLibrary = {
                    navController.navigate(Screen.BookList.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        //  BOOK LIST
        composable(Screen.BookList.route) {
            BookListView(
                onCategoriesClick = {
                    navController.navigate(Screen.CategoryList.route)
                },
                onAddBookClick = {
                    navController.navigate(Screen.AddBook.route)
                },
                onBookClick = { isbn ->
                    navController.navigate(Screen.BookDetail.createRoute(isbn))
                }
            )
        }

        //  BOOK DETAIL
        composable(Screen.BookDetail.route) {
            BookDetailView(
                onBackClick = { navController.popBackStack() }
            )
        }

        //  CATEGORY
        composable(Screen.CategoryList.route) {
            CategoryListView(
                onBackClick = { navController.popBackStack() }
            )
        }

        //  ADD BOOK
        composable(Screen.AddBook.route) {
            AddBookView(
                onBackClick = { navController.popBackStack() },
                onSuccessNavigate = {
                    navController.navigate(Screen.BookList.route) {
                        popUpTo(Screen.AddBook.route) { inclusive = true }
                    }
                }
            )
        }
    }
}