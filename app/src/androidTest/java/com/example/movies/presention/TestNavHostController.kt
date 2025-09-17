package com.example.movies.presention


import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.presention.MovieUiState
import com.example.movies.presention.screen.MovieScreen
import com.example.movies.presention.viewmodel.MovieViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // قائمة أفلام للتست
    private val movies = listOf(
        MovieDto(
            id = 1,
            title = "Tenet",
            overview = "Time inversion",
            poster_path = null,
            release_date = "2020-08-26",
            vote_average = 8.5,
            adult = false,
            original_language = "en"
        ),
        MovieDto(
            id = 2,
            title = "Inception",
            overview = "Dream heist",
            poster_path = null,
            release_date = "2010-07-16",
            vote_average = 9.0,
            adult = false,
            original_language = "en"
        )
    )

    @Test
    fun movieScreen_navigatesToDetails_whenMovieClicked() {
        // 1️⃣ إعداد NavController للاختبار
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        // 2️⃣ عمل mock للـ ViewModel
        val viewModel = mockk<MovieViewModel>()
        every { viewModel.uiState } returns MutableStateFlow<MovieUiState>(
            MovieUiState.Success(movies)
        )

        // 3️⃣ تشغيل الـ Composable مع NavController
        composeTestRule.setContent {
            MovieScreen(
                viewModel = viewModel,
                onMovieClick = { movieId ->
                    navController.navigate("details/$movieId")
                }
            )
        }

        // 4️⃣ الضغط على فيلم "Tenet"
        composeTestRule.onNodeWithText("Tenet").performClick()

        // 5️⃣ التأكد إن NavController انتقل للـ Details Screen مع movieId = 1
        assert(navController.currentDestination?.route?.contains("details/1") == true)
    }

}
