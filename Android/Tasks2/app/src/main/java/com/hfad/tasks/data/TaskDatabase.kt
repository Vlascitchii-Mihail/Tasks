package com.hfad.tasks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    //tells that we use the TaskDao class for handling the Database's requests
    abstract val taskDao: TaskDao

    companion object {

//     @Volatile - Помечает вспомогательное поле JVM аннотированного
//     свойства как изменчивое, что означает, что записи в это поле немедленно
//     становятся видимыми для других потоков.
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context) : TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}