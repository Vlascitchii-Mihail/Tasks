package com.hfad.guessinggame

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    /**
     * An array with possible words
     */
    val words = listOf("Android", "Activity", "Fragment")

    //random() - Returns a random element from this collection.
    val secretWord = words.random().uppercase()

    /**
     * How the word is displayed
     */
    var secretWordDisplay = ""
    var correctGuess = ""
    var incorrectGuesses= ""
    var livesLeft = 8

    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    /**
     * displays the secret word to the display in hidden form
     */
    fun deriveSecretWordDisplay() : String {
        var display = ""
        secretWord.forEach {
            display += checkLatter(it.toString())
        }

        return display
    }

    /**
     * check if user's guess contains in the secret word
     * and display all the correct letters in the secret word on screen
     */
    fun checkLatter(str: String) = when (correctGuess.contains(str)) {
        true -> str
        false -> "_"
    }

    /**
     * considers if the user's guess is correct
     * and add the guess to the correct guess
     * else add to the incorrect guess
     */
    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuess += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuesses += "$guess "
                livesLeft--
            }
        }
    }

    /**
     * game is won if the secretWord == secretWordDisplay
     */
    fun isWon() = secretWord.equals(secretWordDisplay, true)

    /**
     * game is lost if livesLeft == 0
     */
    fun isLost() = livesLeft <= 0

    /**
     * returns the statistics about the game
     */
    fun wonListMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }
}