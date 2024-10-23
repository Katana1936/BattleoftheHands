
package com.example.battleofthehands

enum class Choice(val displayName: String) {
    ROCK("Камень"),
    PAPER("Бумага"),
    SCISSORS("Ножницы");

    companion object {
        fun getRandomChoice(): Choice {
            return values().random()
        }

        fun fromString(value: String): Choice? {
            return values().find { it.displayName.equals(value, ignoreCase = true) }
        }
    }
}
