package dev.mfazio.androidbaseballleague.standings

import android.app.Application
import androidx.lifecycle.*
import dev.mfazio.androidbaseballleague.data.BaseballDatabase
import dev.mfazio.androidbaseballleague.data.BaseballRepository

//AndroidViewModel(application) - parent class which allows us to refer to the Application's object
//to get the context in ViewModel fo creating Repository's object, ViewModel с учетом контекста приложения.
class StandingsViewModel(application: Application): AndroidViewModel(application) {

    private val repo: BaseballRepository

    val standings: LiveData<List<UITeamStanding>>

    init {
        //create a database's object and repository's object
        repo = BaseballDatabase.getDatabase(application, viewModelScope).baseballDao().let { dao ->
            BaseballRepository(dao)
        }

        /**
         * list with UITeams for Recycler view
         */
        standings = Transformations.map(repo.getStandings()) { teamStandings ->
            teamStandings.mapNotNull { teamStanding ->
                UITeamStanding.fromTeamIdAndStandings(teamStanding.teamId, teamStandings)
            }
        }
    }
}