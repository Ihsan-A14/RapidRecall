package com.example.rapidrecall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.rapidrecall.ui.theme.RapidRecallTheme
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapidRecallTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.hsl(190f, 0.441f, 0.850f)) { innerPadding ->
                    when (viewModel.currentScreen.value) {
                        GameViewModel.Navigation.START -> StartScreen(
                            viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )

                        GameViewModel.Navigation.LEVEL_SELECT -> LevelSelectorScreen(
                            viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )

                        GameViewModel.Navigation.MEMORIZE -> GameScreen(
                            viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )

                        GameViewModel.Navigation.INPUT -> GameScreen(
                            viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )

                        GameViewModel.Navigation.RESULT -> ResultScreen(
                            viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

