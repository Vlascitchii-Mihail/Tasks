package dev.mfazio.androidbaseballleague.standings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.mfazio.androidbaseballleague.databinding.StandingsHeaderBinding
import dev.mfazio.androidbaseballleague.databinding.StandingsTeamItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Adapter for statistic's list view
 */
class StandingsAdapter:
    ListAdapter<StandingsListItem, RecyclerView.ViewHolder>(StandingsDiffCallback()) {

    /**
     * Receive the list for Recycler View
     */
        fun addHeadersAndBuildStandings(uiTeamStandings: List<UITeamStanding>) =

        //The default CoroutineDispatcher that is used by all standard builders like launch, async, etc. i
            // f no dispatcher nor any other ContinuationInterceptor is specified in their context.
            CoroutineScope(Dispatchers.Default).launch {
                val items = uiTeamStandings
                    .sortedWith(compareBy({ it.teamStanding.division }, { -it.teamStanding.wins}))
                    .groupBy { it.teamStanding.division }
                    .map { (division, teams) ->
                        listOf(StandingsListItem.Header(division)) + teams.map { team -> StandingsListItem.TeamItem(team) }

                        //Returns a single list of all elements from all collections in the given collection
                    }.flatten()

                withContext(Dispatchers.Main) {

                    //submitList() - Submits (Отправляет) a new list to be diffed (для сравнения), and displayed.
                    submitList(items)
                }
            }

    //Возвращает тип представления элемента в позиции для повторного использования представления.
    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is StandingsListItem.Header -> STANDINGS_ITEM_VIEW_TYPE_HEADER
        is StandingsListItem.TeamItem -> STANDINGS_ITEM_VIEW_TYPE_TEAM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            STANDINGS_ITEM_VIEW_TYPE_HEADER -> StandingsListHeaderViewHolder.from(parent)
            STANDINGS_ITEM_VIEW_TYPE_TEAM -> StandingsListTeamViewHolder.from(parent)
            else -> throw ClassCastException("Unknown view type [$viewType]")
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StandingsListTeamViewHolder -> {
                (getItem(position) as? StandingsListItem.TeamItem)?.let { teamItem ->
                    holder.bind(teamItem)
                }
            }

            is StandingsListHeaderViewHolder -> {
                (getItem(position) as? StandingsListItem.Header)?.let { teamItem ->
                    holder.bind(teamItem)
                }
            }
        }
    }


    class StandingsListTeamViewHolder(private val binding: StandingsTeamItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(standingsTeamItem: StandingsListItem.TeamItem) {
                binding.uiTeamStanding = standingsTeamItem.uiTeamStanding
                /*
                binding.clickListener = View.OnClickListener { view ->
                    val action = NavGraphDirections
                }*/
            }

        companion object {
            fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = StandingsTeamItemBinding.inflate(inflater, parent, false)
                return StandingsListTeamViewHolder(binding)
            }
        }
    }


    class StandingsListHeaderViewHolder(private val binding: StandingsHeaderBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(standingsHeaderItem: StandingsListItem.Header) {
                binding.divisionName = standingsHeaderItem.division.name
            }

            companion object {
                fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                    val inflater = LayoutInflater.from(parent.context)
                    val binding = StandingsHeaderBinding.inflate(inflater, parent, false)
                    return StandingsListHeaderViewHolder(binding)
                }
            }
        }


    companion object {
        private const val STANDINGS_ITEM_VIEW_TYPE_HEADER = 0
        private const val STANDINGS_ITEM_VIEW_TYPE_TEAM = 1
    }
}

//DiffUtil is a utility class that calculates the difference between two lists and outputs
// a list of update operations that converts the first list into the second one.
class StandingsDiffCallback: DiffUtil.ItemCallback<StandingsListItem>() {
    override fun areItemsTheSame(oldIten: StandingsListItem, newItem: StandingsListItem) =
        oldIten == newItem

    override fun areContentsTheSame(oldItem: StandingsListItem, newItem: StandingsListItem) =
        oldItem.id == newItem.id
}