package dev.mfazio.androidbaseballleague.data

import androidx.lifecycle.LiveData
import dev.mfazio.androidbaseballleague.standings.TeamStanding

/**
 * create a new Repository's object
 */
class BaseballRepository(private val baseballDao: BaseBallDao) {
    suspend fun getStandings(): LiveData<List<TeamStanding>> =
        baseballDao.getStandings()

    /**
     * @property instance refers to the repository
     */
    companion object {

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