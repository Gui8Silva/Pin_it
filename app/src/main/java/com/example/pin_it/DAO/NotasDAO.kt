package com.example.pin_it.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pin_it.Entities.Notas

@Dao
interface NotasDAO {

        @Query("SELECT * from notes_table ORDER BY title ASC")
        fun getAlphabetizedNotes(): LiveData<List<Notas>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(notes: Notas)

        @Query("DELETE FROM notes_table")
        suspend fun deleteAll()

        @Query("DELETE FROM notes_table WHERE id == :id")
        fun deleteById(id: Int)

        @Query("UPDATE notes_table SET title=:title, content=:content WHERE id == :id")
        suspend fun updateById(title: String, content: String, id: Int)
}