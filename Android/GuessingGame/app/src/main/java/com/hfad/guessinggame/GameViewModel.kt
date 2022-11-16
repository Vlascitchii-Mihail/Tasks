package com.hfad.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    /**
     * An array with possible words
     */
    private val words = listOf("Android", "Activity", "Fragment")

    //random() - Returns a random element from this collection.
    private val secretWord = words.random().uppercase()

    /**
     * How the word is displayed
     */
    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String> get() = _secretWordDisplay

    private var correctGuess = ""

    private val _incorrectGuesses= MutableLiveData<String>("")
    val  incorrectGuesses: LiveData<String> get() = _incorrectGuesses

    private val _livesLeft = MutableLiveData<Int>(8)
    val livesLeft: LiveData<Int> get() = _livesLeft

    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver: LiveData<Boolean> get() = _gameOver

    init {
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }

    /**
     * displays the secret word to the display in hidden form
     */
    private fun deriveSecretWordDisplay() : String {
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
    private fun checkLatter(str: String) = when (correctGuess.contains(str)) {
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
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                _incorrectGuesses.value += "$guess "
                _livesLeft.value = _livesLeft.value?.minus(1)
            }

            if (isWon() || isLost()) _gameOver.value = true
        }
    }

    /**
     * game is won if the secretWord == secretWordDisplay
     */
    private fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    /**
     * game is lost if livesLeft == 0
     */
    private fun isLost() = (livesLeft.value ?: 0) <= 0

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