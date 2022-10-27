package dev.mfazio.abl.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import dev.mfazio.abl.R
import dev.mfazio.abl.databinding.FragmentStandingsBinding

/**
 *Statistics list view
 */
class StandingsFragment : Fragment() {

    private val standingsViewModel by activityViewModels<StandingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStandingsBinding.inflate(inflater)

        val standingsAdapter = StandingsAdapter()

        binding.standingsList.adapter = standingsAdapter

        //swipe down listener
        binding.standingsSwipeRefreshLayout.setOnRefreshListener {
            standingsViewModel.refreshStandings()
        }

        //standings - list with UITeams for Recycler view
        standingsViewModel.standings.observe(viewLifecycleOwner) { standings ->

            //send the list with UITeams to Adapter
            standingsAdapter.addHeadersAndBuildStandings(standings)

            //Сообщите виджету, что состояние обновления изменилось. Не вызывайте это,
            // когда обновление запускается жестом смахивания.
            binding.standingsSwipeRefreshLayout.isRefreshing = false
        }

        //display the error message in UI
        standingsViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }

            //Сообщите виджету, что состояние обновления изменилось. Не вызывайте это,
            // когда обновление запускается жестом смахивания.
            binding.standingsSwipeRefreshLayout.isRefreshing = false
        }

        standingsViewModel.refreshStandings()

        return binding.root
    }
}