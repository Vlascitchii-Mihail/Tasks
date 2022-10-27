package dev.mfazio.abl.data

import androidx.lifecycle.LiveData
import dev.mfazio.abl.api.services.getDefaultABLService
import dev.mfazio.abl.scoreboard.ScheduledGame
import dev.mfazio.abl.standings.TeamStanding
import dev.mfazio.abl.util.convertToScheduledGames
import dev.mfazio.abl.util.convertToTeamStandings
import dev.mfazio.abl.util.toGameDateString
import java.io.IOException
import java.time.LocalDate

/**
 * create a new Repository's object
 */
class BaseballRepository(private val baseballDao: BaseballDao) {

    fun getStandings(): LiveData<List<TeamStanding>> =
        baseballDao.getStandings()

    /**
     * get data from ABL API client
     */
    suspend fun updateStandings(): ResultStatus {
        val standingsResult = safeApiRequest {

            //getStandings() - get data from ABL API client
            apiService.getStandings()
        }

        return if (
            standingsResult.success &&
            standingsResult.result?.any() == true
        ) {
            baseballDao.updateStandings(
                standingsResult.result.convertToTeamStandings(
                    baseballDao.getCurrentStandings()
                )
            )
            ResultStatus.Success
        } else {
            standingsResult.status
        }
    }

    fun getGamesForDate(date: LocalDate): LiveData<List<ScheduledGame>> =
        baseballDao.getGamesForDate("${date.toGameDateString()}%")

    suspend fun updateGamesForDate(date: LocalDate): ResultStatus {
        val gamesResult = safeApiRequest {
            apiService.getGames(requestedDate = date)
        }

        return if (gamesResult.success && gamesResult.result?.any() == true) {
            baseballDao.insertOrUpdateGames(
                gamesResult.result.convertToScheduledGames()
            )
            ResultStatus.Success
        } else {
            gamesResult.status
        }
    }

    /**
     * variants of the exceptions
     */
    enum class ResultStatus {
        Unknown,
        Success,
        NetworkException,
        RequestException,
        GeneralException
    }


    /**
     * Result of the updating the StandingsFragment
     */
    inner class ApiResult<T>(
        val result: T? = null,
        val status: ResultStatus = ResultStatus.Unknown
    ) {
        val success = status == ResultStatus.Success
    }


    /**
     * catch the exceptions and return the description of the exception
     * @param apiFunction function which can return an exception
     */
    private suspend fun <T> safeApiRequest(
        apiFunction: suspend () -> T
    ): ApiResult<T> =
        try {
            val result = apiFunction()
            ApiResult(result, ResultStatus.Success)
        } catch (ex: retrofit2.HttpException) {
            ApiResult(status = ResultStatus.RequestException)
        } catch (ex: IOException) {
            ApiResult(status = ResultStatus.NetworkException)
        } catch (ex: Exception) {
            ApiResult(status = ResultStatus.GeneralException)
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
        fun getInstance(baseballDao: BaseballDao) =
            this.instance ?: synchronized(this) {
                instance ?: BaseballRepository(baseballDao).also {
                    instance = it
                }
            }
    }
}