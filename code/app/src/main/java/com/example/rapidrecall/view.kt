package com.example.rapidrecall

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import java.time.format.DateTimeFormatter

@Composable
fun StartScreen(viewModel: GameViewModel, modifier: Modifier = Modifier)
{
    val history = viewModel.getHistory()

    Column(modifier = Modifier.padding(top = 64.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(64.dp))

        Text("Rapid Recall", fontFamily = FontFamily.Monospace, fontSize = 32.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {viewModel.onStartButtonSelect()}, modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2C3E50),
                contentColor = Color.White),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)) {
            Text("Choose Level")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Attempt Summary", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)

        Row() {
            Text("Accuracy = ${viewModel.getAccuracy()}%", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            Spacer(modifier = modifier.width(16.dp))
            Text("Total Games = ${viewModel.getTotalGames()}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            Spacer(modifier = modifier.width(16.dp))
            Text("Total Wins = ${viewModel.getTotalWins()}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        }


        Spacer(modifier = modifier.height(16.dp))

        Text("Attempt History", fontSize = 22.sp)

        Row(modifier = Modifier.padding(top = 32.dp).fillMaxWidth()) {
            Text("Length", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Target", modifier = Modifier.weight(2f), textAlign = TextAlign.Center)
            Text("Guess", modifier = Modifier.weight(2f), textAlign = TextAlign.Center)
            Text("Result", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Time", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
        HorizontalDivider(thickness = 2.dp, color = Color.Black)


        if (history.isNotEmpty()) {
            history.forEach { record ->
                Row(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp).fillMaxWidth()) {
                    Text(record.seq_length.toString(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(record.targetSequence, modifier = Modifier.weight(2f), textAlign = TextAlign.Center)
                    Text(record.userSeq, modifier = Modifier.weight(2f), textAlign = TextAlign.Center)

                    val resultColor = if (record.isCorrect)
                        Color(0xFF2E7D32) // Green
                    else
                        Color(0xFFC62828) // Red

                    Text(
                        text = if (record.isCorrect) "Pass" else "Fail",
                        modifier = Modifier.weight(1.5f),
                        color = resultColor,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    val timeString = record.timestamp.format(DateTimeFormatter.ofPattern("MM/dd HH:mm"))
                    Text(timeString, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                }
                HorizontalDivider(thickness = 2.dp, color = Color.Black)
                }
            }
        }
}


@Composable
fun LevelSelectorScreen(viewModel: GameViewModel, modifier: Modifier = Modifier)
{
    var sliderPosition by remember { mutableFloatStateOf(5f) }

    Column(modifier = Modifier.padding(32.dp).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(64.dp))

        Text("Choose the length of sequence to be displayed", fontFamily = FontFamily.Monospace, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(48.dp))

        Slider(value = sliderPosition, onValueChange = {sliderPosition = it}, valueRange = 1f..9f, steps = 7, modifier = Modifier.fillMaxWidth(1f),
            colors = SliderDefaults.colors(thumbColor = Color(0xFF2C3E50), activeTrackColor = Color(0xFF2C3E50), inactiveTrackColor = Color.DarkGray.copy(alpha = 0.3f)))

        Text("Length: ${sliderPosition.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {viewModel.onLevelSelected(sliderPosition.toInt())},
            modifier = Modifier.fillMaxWidth(0.8f).height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2C3E50),
                contentColor = Color.White),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ){
            Text("Start Game")
        }

    }
}

@Composable
fun GameScreen(viewModel: GameViewModel, modifier: Modifier = Modifier){

    var userInput by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(32.dp).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        if(viewModel.currentScreen.value == GameViewModel.Navigation.MEMORIZE){
            Box(
                modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(24.dp)).padding(horizontal = 64.dp, vertical = 48.dp),
                contentAlignment = Alignment.Center
            ){
                Text(viewModel.currentSequence.value, fontSize = 72.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace, color = Color(0xFF2C3E50))
            }
        }
        else{
            Text("Enter the sequence below:", fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(24.dp))

            TextField(value = userInput, onValueChange = {userInput = it}, modifier = Modifier.fillMaxWidth(0.8f), textStyle = androidx.compose.material3.LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 24.sp, fontWeight = FontWeight.Bold))

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {viewModel.submitGuess(userInput)}, modifier = Modifier.fillMaxWidth(0.8f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2C3E50),
                    contentColor = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ){
                Text("Submit Your Guess", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ResultScreen(viewModel: GameViewModel, modifier: Modifier = Modifier){

    Column(modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val record = viewModel.currentResult.value
        if (record == null) {
            Text("Error loading Result, Try Again", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        else{
            if (record.isCorrect){
                Text("Woo ho! Correct", fontSize = 40.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
            }
            else{
                Text("Uh Oh! Incorrect", fontSize = 40.sp, color = Color(0xFFC62828), fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(text = "Target Sequence:", fontSize = 18.sp, color = Color.DarkGray)
            Text(text = record.targetSequence, fontSize = 28.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Your Guess:", fontSize = 18.sp, color = Color.DarkGray)
            Text(text = record.userSeq, fontSize = 28.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(onClick = {viewModel.onResetClicked()}, modifier = Modifier.fillMaxWidth(0.8f).height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2C3E50),
                contentColor = Color.White),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)){
            Text("Go back to Start Page")
        }
    }
}