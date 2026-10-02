package com.example.rapidrecall

import android.R
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

    fun onLevelSelected(length: Int) {
        currentScreen.value = Navigation.MEMORIZE
        val target = GameModel.generateNewSequence(length)

        viewModelScope.launch {
            for (i in 0..length - 1) {
                currentSequence.value = target[i].toString()
                delay(800.milliseconds)

                currentSequence.value = ""
                delay(10.milliseconds)
            }

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

    fun getAccuracy(): Double = GameModel.accuracy()
    fun getHistory(): List<AttemptRecord> = GameModel.AttemptHistory
    fun getTotalGames(): Int = GameModel.AttemptHistory.size
    fun getTotalWins(): Int = GameModel.AttemptHistory.count{it.isCorrect}
}