package com.example.rapidrecall

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class GameViewModel: ViewModel() {
    private val GameModel = GameModel()

    enum class Navigation{
        START, LEVEL_SELECT, MEMORIZE, INPUT, RESULT
    }

    var currentScreen = mutableStateOf(Navigation.START)
    var currentSequence = mutableStateOf("")

    var currentResult = mutableStateOf<AttemptRecord?>(null)

    fun onStartButtonSelect(){
        currentScreen.value = Navigation.LEVEL_SELECT
    }

    fun onLevelSelected(length: Int){
        currentScreen.value = Navigation.MEMORIZE
        val target = GameModel.generateNewSequence(length)

        currentSequence.value = target
        viewModelScope.launch {
            delay(2000.milliseconds)
            currentScreen.value = Navigation.INPUT
        }
    }

    fun submitGuess(userInput: String){
        val result = GameModel.evaluateAnswer(userInput)
        currentScreen.value = Navigation.RESULT
        currentResult.value = result

    }

    fun onResetClicked(){
        currentScreen.value = Navigation.START
        currentSequence.value = ""
        currentResult.value = null
    }
}