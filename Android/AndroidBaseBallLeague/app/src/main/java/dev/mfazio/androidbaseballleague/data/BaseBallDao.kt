package dev.mfazio.androidbaseballleague.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.mfazio.androidbaseballleague.standings.TeamStanding

@Dao
abstract class BaseBallDao {
    @Insert
    abstract suspend fun insertStandings(standings: List<TeamStanding>)

    @Update
    abstract suspend fun updateStandings(standings: List<TeamStanding>)

    @Query("SELECT * FROM standings")
    abstract suspend fun getStandings(): LiveData<List<TeamStanding>>

    @Query("SELECT * FROM standings")
    abstract suspend fun getCurrentStandings(): List<TeamStanding>
}