package dev.mfazio.androidbaseballleague.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StandingsViewModel: ViewModel() {

    /**
     * list with UITeams for Recycler view
     */
    val standings: LiveData<List<UITeamStanding>> = MutableLiveData(
        TeamStanding.mockTeamStandings.mapNotNull { teamStanding ->
            UITeamStanding.fromTeamIdAndStandings(
                teamStanding.teamId, TeamStanding.mockTeamStandings
            )
        }
    )
}