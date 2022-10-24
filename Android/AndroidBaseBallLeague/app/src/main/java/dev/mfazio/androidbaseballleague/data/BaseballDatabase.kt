package dev.mfazio.androidbaseballleague.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mfazio.androidbaseballleague.standings.TeamStanding
import kotlinx.coroutines.CoroutineScope

@TypeConverters(Converters::class)
@Database(entities = [TeamStanding::class], exportSchema = false, version = 1)
abstract class BaseballDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var Instance: BaseballDatabase? = null

        /**
         * @return a database's exemplar
         */
        fun getDatabase(context: Context, scope: CoroutineScope): BaseballDatabase =

            //synchronized() - Выполняет данный функциональный блок, удерживая монитор блокировки данного объекта.
            Instance ?: synchronized(this) {

                //Room.databaseBuilder() - Creates a RoomDatabase.Builder for a persistent database.
                /**
                 * @param context app's context -
                 * @param BaseballDatabase::class.java database's class
                 * @param BaseballDatabase database's name
                 */
                val instance = Room.databaseBuilder(
                    context,
                    BaseballDatabase::class.java,
                    "BaseballDatabase"
                ).build()

                Instance = instance

                instance
            }
    }
}