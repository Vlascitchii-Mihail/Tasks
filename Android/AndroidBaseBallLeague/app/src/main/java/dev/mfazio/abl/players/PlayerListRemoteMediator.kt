package dev.mfazio.abl.players

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.mfazio.abl.api.services.AndroidBaseballLeagueService
import dev.mfazio.abl.data.BaseballDatabase
import java.io.InvalidObjectException

/**
 * figuring out which data needs to be displayed next in the list
 */
@ExperimentalPagingApi
class PlayerListRemoteMediator(
    private val apiService: AndroidBaseballLeagueService,
    private val baseballDatabase: BaseballDatabase,
    private val teamId: String? = null,
    private val nameQuery: String? = null
): RemoteMediator<Int, PlayerListItem>() {


    //@param PagingState holds the previous loaded pages, where we are in the list
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlayerListItem>
        ): MediatorResult {

        //loading the required page
        val page = when(loadType) {

            //Load at the start of a PagingData.
            LoadType.PREPEND -> {
                val keys = loadKeysForFirstPlayer(state) ?: return MediatorResult.Error(
                    InvalidObjectException("Keys should not be null for $loadType")
                )

                keys.previousKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            //Load at the end of a PagingData.
            LoadType.APPEND -> {
                val keys = loadKeysForLastPlayer(state)

                keys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
            }

            //figuring out where to start loading new data
            LoadType.REFRESH -> {
                val keys = loadKeysForClosestPlayer(state)

                keys?.nextKey ?: startingPageIndex
            }
        }

        return loadAndSaveApiData(page, state, loadType == LoadType.REFRESH)
    }

    /**
     * return first key
     */
    private suspend fun loadKeysForFirstPlayer(
        state: PagingState<Int, PlayerListItem>
    ) = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { player ->
        baseballDatabase.playerKeysDao().getPlayerKeysByPlayerId(player.playerId)
    }

    /**
     * return last key
     */
    private suspend fun loadKeysForLastPlayer(
        state: PagingState<Int, PlayerListItem>
    ) = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { player ->
        baseballDatabase.playerKeysDao().getPlayerKeysByPlayerId(player.playerId)
    }

    /**
     * where to start loading new data
     */
    private suspend fun loadKeysForClosestPlayer(
        state: PagingState<Int, PlayerListItem>

    //anchorPosition - Самый последний использованный индекс в списке, включая заполнители.
    ) = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.playerId?.let { playerId ->
            baseballDatabase.playerKeysDao().getPlayerKeysByPlayerId(playerId)
        }
    }

    companion object {
        private const val startingPageIndex = 0
    }
}