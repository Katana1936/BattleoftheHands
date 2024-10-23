// File: app/src/main/java/com/example/battleofthehands/GameScreen.kt

package com.example.battleofthehands

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource

@Composable
fun GameScreen(modifier: Modifier = Modifier) {

    var gameOutcome by remember { mutableStateOf<GameOutcome?>(null) }

    var userScore by remember { mutableStateOf(0) }
    var computerScore by remember { mutableStateOf(0) }


    var gameHistory by remember { mutableStateOf(listOf<GameOutcome>()) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Камень, Ножницы, Бумага",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ScoreCard(label = "Ваш счёт", score = userScore)
            ScoreCard(label = "Счёт компьютера", score = computerScore)
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton(choice = Choice.ROCK, onChoiceSelected = { choice ->
                playGame(choice) { outcome ->
                    gameOutcome = outcome
                    when (outcome.result) {
                        GameResult.WIN -> userScore++
                        GameResult.LOSE -> computerScore++
                        GameResult.DRAW -> {}
                    }
                    gameHistory = listOf(outcome) + gameHistory
                }
            })
            ChoiceButton(choice = Choice.PAPER, onChoiceSelected = { choice ->
                playGame(choice) { outcome ->
                    gameOutcome = outcome
                    when (outcome.result) {
                        GameResult.WIN -> userScore++
                        GameResult.LOSE -> computerScore++
                        GameResult.DRAW -> {}
                    }
                    gameHistory = listOf(outcome) + gameHistory
                }
            })
            ChoiceButton(choice = Choice.SCISSORS, onChoiceSelected = { choice ->
                playGame(choice) { outcome ->
                    gameOutcome = outcome
                    when (outcome.result) {
                        GameResult.WIN -> userScore++
                        GameResult.LOSE -> computerScore++
                        GameResult.DRAW -> {}
                    }
                    gameHistory = listOf(outcome) + gameHistory
                }
            })
        }

        Spacer(modifier = Modifier.height(24.dp))


        gameOutcome?.let { outcome ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Вы выбрали: ${outcome.userChoice.displayName}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Компьютер выбрал: ${outcome.computerChoice.displayName}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = outcome.result.message,
                        style = MaterialTheme.typography.headlineSmall,
                        color = when (outcome.result) {
                            GameResult.WIN -> MaterialTheme.colorScheme.primary
                            GameResult.LOSE -> MaterialTheme.colorScheme.error
                            GameResult.DRAW -> MaterialTheme.colorScheme.secondary
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { gameOutcome = null }) {
                        Text("Играть снова")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        if (gameHistory.isNotEmpty()) {
            Text(
                text = "История игр:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items<GameOutcome>(gameHistory) { outcome ->
                    GameHistoryItem(outcome = outcome)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                userScore = 0
                computerScore = 0
                gameHistory = listOf()
                gameOutcome = null
            }) {
                Text("Сбросить счёт")
            }
        }
    }
}

@Composable
fun ChoiceButton(choice: Choice, onChoiceSelected: (Choice) -> Unit) {
    Button(
        onClick = { onChoiceSelected(choice) },
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
            .height(120.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (choice) {
                Choice.ROCK -> Icon(
                    painter = painterResource(id = R.drawable.rock),
                    contentDescription = "Камень",
                    modifier = Modifier.size(48.dp)
                )
                Choice.PAPER -> Icon(
                    painter = painterResource(id = R.drawable.paper),
                    contentDescription = "Бумага",
                    modifier = Modifier.size(48.dp)
                )
                Choice.SCISSORS -> Icon(
                    painter = painterResource(id = R.drawable.scissors),
                    contentDescription = "Ножницы",
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = choice.displayName)
        }
    }
}

@Composable
fun ScoreCard(label: String, score: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = "$score", style = MaterialTheme.typography.headlineSmall)
    }
}

fun playGame(choice: Choice, onResult: (GameOutcome) -> Unit) {
    val computer = Choice.getRandomChoice()
    val outcome = determineGameOutcome(choice, computer)
    onResult(outcome)
}

@Composable
fun GameHistoryItem(outcome: GameOutcome) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Вы: ${outcome.userChoice.displayName}")
                Text(text = "Компьютер: ${outcome.computerChoice.displayName}")
            }
            Text(
                text = outcome.result.message,
                color = when (outcome.result) {
                    GameResult.WIN -> MaterialTheme.colorScheme.primary
                    GameResult.LOSE -> MaterialTheme.colorScheme.error
                    GameResult.DRAW -> MaterialTheme.colorScheme.secondary
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
