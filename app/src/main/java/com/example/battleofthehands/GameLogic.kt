package com.example.battleofthehands

data class GameOutcome(
    val userChoice: Choice,
    val computerChoice: Choice,
    val result: GameResult
)

fun determineGameOutcome(userChoice: Choice, computerChoice: Choice): GameOutcome {
    val result = GameResult.determineResult(userChoice, computerChoice)
    return GameOutcome(userChoice, computerChoice, result)
}