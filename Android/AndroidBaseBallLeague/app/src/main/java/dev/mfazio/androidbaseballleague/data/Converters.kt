package dev.mfazio.androidbaseballleague.data

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dev.mfazio.androidbaseballleague.standings.WinLoss
import dev.mfazio.androidbaseballleague.team.Division

class Converters {

    /**
     * convert data from Division to Int
     */
    @TypeConverter
    fun fromDivision(division: Division?) =
        division?.ordinal ?: Division.Unknown.ordinal

    /**
     * convert data fom Int to Division
     */
    @TypeConverter
    fun toDivision(divisionOrdinal: Int?) =
        if (divisionOrdinal != null) Division.values()[divisionOrdinal]
        else Division.Unknown

    /**
     * convert data from WinLoss to Int
     */
    @TypeConverter
    fun fromWinLoss(winLoss: WinLoss?) =
        winLoss?.ordinal ?: WinLoss.Unknown.ordinal

    /**
     * convert data from Int to WinLoss
     */
    @TypeConverter
    fun toWinLoss(winLossOrdinal: Int?) =
        if (winLossOrdinal != null) WinLoss.values()[winLossOrdinal]
        else WinLoss.Unknown
}