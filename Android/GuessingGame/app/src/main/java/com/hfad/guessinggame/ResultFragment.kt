package com.hfad.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentResultBinding

/**
 * A simple [Fragment] subclass.
 */
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    //works even we hav viewModel's constructor
    //private val viewModel by activityViewModels<ResultViewModel> ()
    lateinit var viewModel: ResultViewModel
    lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root

        //receive data from args
        val result = ResultFragmentArgs.fromBundle(requireArguments()).result

        //create a viewModelFactory object
        viewModelFactory = ResultViewModelFactory(result)

        //get the ResultViewModel object
        viewModel = ViewModelProvider(this, viewModelFactory)[ResultViewModel::class.java]

        binding.wonLost.text = viewModel.result

        binding.newGameButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}