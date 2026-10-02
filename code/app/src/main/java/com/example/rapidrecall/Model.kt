package com.example.rapidrecall

import java.time.LocalDateTime
import kotlin.random.Random

class AttemptRecord(
    val seq_length: Int,
    val targetSequence: String,
    val userSeq: String,
    val isCorrect: Boolean,
    val timestamp: LocalDateTime
){}

class GameModel{
    private var currentLength: Int = 0
    private var currentTarSeq: String = ""
    private val AttemptHistory = mutableListOf<AttemptRecord>()

    fun generateNewSequence(length: Int): String{
        currentLength = length
        var target = ""
        for (i in 1..length) {
            target = target + Random.nextInt(10)
        }
        currentTarSeq = target
        return currentTarSeq
    }

    fun evaluateAnswer(userInput: String): AttemptRecord{
        if (userInput == currentTarSeq){
            AttemptHistory.add(AttemptRecord(currentLength, currentTarSeq, userInput, true, LocalDateTime.now()))
        }
        else{
            AttemptHistory.add(AttemptRecord(currentLength, currentTarSeq, userInput, false, LocalDateTime.now()))
        }
        return AttemptHistory.last()
    }

    fun accuracy(): Double{
        if (AttemptHistory.isEmpty()) return 0.0
        val wins = AttemptHistory.count{it.isCorrect}
        return (wins.toDouble() / AttemptHistory.size) * 100
    }
}
