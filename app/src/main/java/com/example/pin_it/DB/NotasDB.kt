package com.example.pin_it.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pin_it.DAO.NotasDAO
import com.example.pin_it.Entities.Notas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Notas::class), version = 1, exportSchema = false)

public abstract class NotasDB : RoomDatabase() {

    abstract fun notasDAO(): NotasDAO

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var notasDAO = database.notasDAO()
                    //Delete all content here.
                    /*notasDAO.deleteAll()

                    //Add sample words.
                    var note = Note(1, "Titulo", "Conteudo")
                        notasDAO.insert(note)
                        note = Note(2, "Titulo 2", "Conteudo 2")
                        notasDAO.insert(note)
                     */
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NotasDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotasDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotasDB::class.java,
                    "notes_database"
                )
                    //.fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

}