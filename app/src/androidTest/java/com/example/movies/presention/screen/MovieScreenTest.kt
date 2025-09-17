package com.example.movies.presention.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.presention.MovieUiState
import com.example.movies.presention.viewmodel.MovieViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieScreenTest{
    @get:Rule
    val composeTestRule = createComposeRule()

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
    fun movieScreen_showsLoadingIndicator_whenStateIsLoading() {
        // 1️⃣ عمل mock لـ ViewModel
        val viewModel = mockk<MovieViewModel>()
        val mockStateFlow = MutableStateFlow<MovieUiState>(MovieUiState.Loading)

        every { viewModel.uiState } returns mockStateFlow

        // 2️⃣ تشغيل الـ Composable
        composeTestRule.setContent {
            MovieScreen(viewModel = viewModel, onMovieClick = {})
        }

        // 3️⃣ Assertion: يتأكد إن CircularProgressIndicator موجود
        composeTestRule.onNodeWithTag("loading_indicator")
            .assertIsDisplayed()
    }
    @Test
    fun movieScreen_showsMovieList_whenStateIsSuccess() {
        val viewModel = mockk<MovieViewModel>()
        val mockStateFlow = MutableStateFlow<MovieUiState>(MovieUiState.Success(movies))
        every { viewModel.uiState } returns mockStateFlow

        composeTestRule.setContent {
            MovieScreen(viewModel = viewModel, onMovieClick = {})
        }

        // 1️⃣ تأكد إن أول فيلم موجود
        composeTestRule.onNodeWithText("Tenet").assertIsDisplayed()

        // 2️⃣ تأكد إن ثاني فيلم موجود
        composeTestRule.onNodeWithText("Inception").assertIsDisplayed()
    }

    @Test
    fun movieScreen_showsErrorMessage_whenStateIsError() {
        val viewModel = mockk<MovieViewModel>()
        val errorMessage = "Network error"
        every { viewModel.uiState } returns MutableStateFlow<MovieUiState>(MovieUiState.Error(errorMessage))

        composeTestRule.setContent {
            MovieScreen(viewModel = viewModel, onMovieClick = {})
        }

        // Assertion: رسالة الخطأ موجودة
        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()
    }

    @Test
    fun movieScreen_clickMovie_callsOnMovieClick() {
        val viewModel = mockk<MovieViewModel>()
        every { viewModel.uiState } returns MutableStateFlow(MovieUiState.Success(movies))

        var clickedMovieId: Int? = null

        composeTestRule.setContent {
            MovieScreen(viewModel = viewModel, onMovieClick = { clickedMovieId = it })
        }

        composeTestRule.onNodeWithText("Inception").performClick()

        assert(clickedMovieId == 2)
    }

    @Test
    fun movieScreen_navigatesToDetails_whenMovieClicked() {
        val viewModel = mockk<MovieViewModel>()
        val mockStateFlow = MutableStateFlow<MovieUiState>(MovieUiState.Success(movies))
        every { viewModel.uiState } returns mockStateFlow

        // هنا هنمسك movieId اللي اتضغط
        var clickedMovieId: Int? = null

        composeTestRule.setContent {
            MovieScreen(
                viewModel = viewModel,
                onMovieClick = { clickedMovieId = it }
            )
        }

        // الضغط على فيلم "Tenet"
        composeTestRule.onNodeWithText("Tenet").performClick()

        // Assert: تأكد إن الـ lambda اتنادت بالـ movieId الصح
        assert(clickedMovieId == 1)
    }

}