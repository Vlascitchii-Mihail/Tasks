package com.hfad.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass..
 */
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    //add a GameViewModel exemplar
    //private val viewModel by activityViewModels<GameViewModel> ()
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        //add a GameViewModel exemplar
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer{ newValue ->
            binding.incorrectGuesses.text = "Incorrect guess: $newValue"
        })

        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
            binding.lives.text = "You have $newValue lives left."
        })

        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
            binding.word.text = newValue
        })

        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue) {

                //send the data to the another fragment
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonListMessage())
                view.findNavController().navigate(action)
            }
        })

        binding.guessButton.setOnClickListener() {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}