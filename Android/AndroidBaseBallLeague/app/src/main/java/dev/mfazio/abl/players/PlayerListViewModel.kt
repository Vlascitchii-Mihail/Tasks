package dev.mfazio.abl.players

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import dev.mfazio.abl.data.BaseballDatabase
import dev.mfazio.abl.data.BaseballRepository
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class PlayerListViewModel(application: Application): AndroidViewModel(application) {
    private val repo:BaseballRepository

    init {
        repo = BaseballDatabase.getDatabase(application, viewModelScope).let { db ->
            BaseballRepository.getInstance(db)
        }
    }

    /**
     * call the function getPlayerListItem() from BaseballRepository
     */
    fun getPlayerListItem(teamId: String? = null, nameQuery: String? = null): Flow<PagingData<PlayerListItem>> {

    }
}