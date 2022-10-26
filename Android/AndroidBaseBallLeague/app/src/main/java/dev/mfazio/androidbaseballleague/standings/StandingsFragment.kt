package dev.mfazio.androidbaseballleague.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.mfazio.androidbaseballleague.databinding.FragmentStandingsBinding

/**
 *Statistics list view
 */
class StandingsFragment: Fragment() {

    private val standingsViewModel by activityViewModels<StandingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val view = inflater.inflate(R.layout.fragment_standings, container, false)

        val binding = FragmentStandingsBinding.inflate(inflater)

        val standingsAdapter = StandingsAdapter()

//        //smart cast
//        if (view is RecyclerView) {
//            view.adapter = standingsAdapter
//        }

        binding.standingsList.adapter = standingsAdapter

        //swipe down listener
        binding.standingSwipeRefreshLayout.setOnRefreshListener {
            standingsViewModel.refreshStandings()
        }

        //LiveData.observe - listener's registration
//         viewLifecycleOwner - Get a LifecycleOwner that represents the Fragment's View lifecycle.
//         fragment's view lifecycle. Снимает слушателя после уничтожения фрагмента

//         Observer - reaction to a new data (lambda) Adds the given observer to the observers list within
//         the lifespan (срока жизни) of the given owner. The events are dispatched on the main thread.

        //standings - list with UITeams for Recycler view
        standingsViewModel.standings.observe(viewLifecycleOwner) { standings ->

            //send the list with UITeams to Adapter
            standingsAdapter.addHeadersAndBuildStandings(standings)

            //Сообщите виджету, что состояние обновления изменилось. Не вызывайте это,
            // когда обновление запускается жестом смахивания.
            binding.standingSwipeRefreshLayout.isRefreshing = false
        }

        //display the error message in UI
        standingsViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }

            //Сообщите виджету, что состояние обновления изменилось. Не вызывайте это,
            // когда обновление запускается жестом смахивания.
            binding.standingSwipeRefreshLayout.isRefreshing = false
        }

        standingsViewModel.refreshStandings()

//        return view

        return binding.root
    }
}