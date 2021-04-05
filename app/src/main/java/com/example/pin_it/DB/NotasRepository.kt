package com.example.pin_it.DB

import androidx.lifecycle.LiveData
import com.example.pin_it.DAO.NotasDAO
import com.example.pin_it.Entities.Notas


class NotasRepository(private val notasDAO: NotasDAO) {

    val allNotes: LiveData<List<Notas>> = notasDAO.getAlphabetizedNotes()

    suspend fun insert(note: Notas) {
        notasDAO.insert(note)
    }

    fun deleteById(id: Int) {
        notasDAO.deleteById(id)
    }
    suspend fun updateById(title: String, content: String, id: Int){
        notasDAO.updateById(title, content, id)
    }
}