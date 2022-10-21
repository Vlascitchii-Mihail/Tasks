package dev.mfazio.androidbaseballleague.standings

import dev.mfazio.androidbaseballleague.team.Division

sealed class StandingsListItem {
    abstract val id: String

    data class TeamItem(val uiTeamStanding: UITeamStanding): StandingsListItem() {
        override val id = uiTeamStanding.teamId
    }

    data class Header(val division: Division): StandingsListItem() {
        override val id = division.name
    }
}