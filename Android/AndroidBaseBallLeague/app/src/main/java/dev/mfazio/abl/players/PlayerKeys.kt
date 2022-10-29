package dev.mfazio.abl.players

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * connect(PlayerListItem via playerId to previous and next pages) pages in the Paging
 */
@Entity(tableName = "player_keys")
data class PlayerKeys(
    @PrimaryKey val playerId: String,
    val previousKey: Int?,
    val nextKey: Int?
)