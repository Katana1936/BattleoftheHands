package com.example.battleofthehands

enum class GameResult(val message: String) {
    WIN("Вы выиграли!"),
    LOSE("Вы проиграли!"),
    DRAW("Ничья!");

    companion object {
        fun determineResult(userChoice: Choice, computerChoice: Choice): GameResult {
            if (userChoice == computerChoice) {
                return DRAW
            }
            return when (userChoice) {
                Choice.ROCK -> if (computerChoice == Choice.SCISSORS) WIN else LOSE
                Choice.PAPER -> if (computerChoice == Choice.ROCK) WIN else LOSE
                Choice.SCISSORS -> if (computerChoice == Choice.PAPER) WIN else LOSE
            }
        }
    }
}
