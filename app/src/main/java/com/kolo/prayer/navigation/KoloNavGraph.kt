package com.kolo.prayer.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.kolo.prayer.ui.bookdetail.BookDetailScreen
import com.kolo.prayer.ui.home.HomeScreen
import com.kolo.prayer.ui.onboarding.OnboardingScreen
import com.kolo.prayer.ui.reader.ReaderScreen
import com.kolo.prayer.ui.settings.SettingsScreen

object Routes {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val BOOK_DETAIL = "book/{bookId}"
    const val READER = "reader/{bookId}/{sectionId}"
    const val SETTINGS = "settings"

    fun bookDetail(bookId: String) = "book/$bookId"
    fun reader(bookId: String, sectionId: String) = "reader/$bookId/$sectionId"
}

@Composable
fun KoloNavGraph(
    startDestination: String = Routes.ONBOARDING,
    onSettingsClick: () -> Unit = {},
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it / 3 }) + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it / 3 }) + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        },
    ) {
        // ── Onboarding ──
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onComplete = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // ── Home ──
        composable(Routes.HOME) {
            HomeScreen(
                onBookClick = { bookId ->
                    navController.navigate(Routes.bookDetail(bookId))
                },
                onSettingsClick = {
                    navController.navigate(Routes.SETTINGS)
                },
            )
        }

        // ── Book Detail ──
        composable(
            route = Routes.BOOK_DETAIL,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType }),
        ) {
            BookDetailScreen(
                onBack = { navController.popBackStack() },
                onSectionClick = { bookId, sectionId ->
                    navController.navigate(Routes.reader(bookId, sectionId))
                },
            )
        }

        // ── Reader ──
        composable(
            route = Routes.READER,
            arguments = listOf(
                navArgument("bookId") { type = NavType.StringType },
                navArgument("sectionId") { type = NavType.StringType },
            ),
        ) {
            ReaderScreen(
                onBack = { navController.popBackStack() },
            )
        }

        // ── Settings ──
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}
