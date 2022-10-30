package dev.mfazio.abl.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.mfazio.abl.databinding.FragmentPlayersBinding

class PlayersFragment: Fragment() {
    private val playerListArgs: PlayersFragmentArgs by navArgs()

    //with paging
    private lateinit var playersAdapter: PlayersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlayersBinding.inflate(inflater)
        this.playersAdapter = PlayersAdapter()

        with(binding.playersList) {
            adapter = playersAdapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }

        return binding.root
    }
}