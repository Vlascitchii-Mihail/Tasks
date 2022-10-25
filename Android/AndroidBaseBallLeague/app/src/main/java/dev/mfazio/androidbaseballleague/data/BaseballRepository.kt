package dev.mfazio.androidbaseballleague.data

import androidx.lifecycle.LiveData
import dev.mfazio.abl.api.services.getDefaultABLService
import dev.mfazio.abl.util.convertToTeamStandings
import dev.mfazio.androidbaseballleague.standings.TeamStanding

/**
 * create a new Repository's object
 */
class BaseballRepository(private val baseballDao: BaseBallDao) {
    fun getStandings(): LiveData<List<TeamStanding>> =
        baseballDao.getStandings()

    /**
     * get data from ABL API client
     */
    suspend fun updateStandings() {

        //getStandings() - get data from ABL API client
        val standings = apiService.getStandings()

        //any() - Returns true if collection has at least one element.
        if (standings.any()) {
            baseballDao.updateStandings(
                standings.convertToTeamStandings(baseballDao.getCurrentStandings())
            )
        }
    }

    /**
     * @property instance refers to the repository
     */
    companion object {

        /**
         * reference to ABL API client
         */
        private val apiService = getDefaultABLService()

        //Помечает вспомогательное поле JVM аннотированного свойства как изменчивое,
        // что означает, что записи в это поле немедленно становятся видимыми для других потоков.
        @Volatile
        private var instance: BaseballRepository? = null

        /**
         * create a new Repository's object
         */
        fun getInstance(baseballDao: BaseBallDao) =
            this.instance ?: synchronized(this) {
                instance ?: BaseballRepository(baseballDao).also {
                    instance = it
                }
            }
    }
}