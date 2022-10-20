package dev.mfazio.androidbaseballleague.standings

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Adapter for statistic's list view
 */
class StandingsAdapter:
    ListAdapter<StandingsListItem> {

        fun addHeadersAndBuildStandings() =

        //The default CoroutineDispatcher that is used by all standard builders like launch, async, etc. i
            // f no dispatcher nor any other ContinuationInterceptor is specified in their context.
            CoroutineScope(Dispatchers.Default).launch {
                val items =
            }
}