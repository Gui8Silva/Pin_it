package com.example.pin_it.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.pin_it.DB.NotasDB
import com.example.pin_it.DB.NotasRepository
import com.example.pin_it.Entities.Notas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotasViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotasRepository

    val allNotes: LiveData<List<Notas>>
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    init {
        val notesDao = NotasDB.getDatabase(application, viewModelScope).notasDAO()
        repository = NotasRepository(notesDao)
        allNotes = repository.allNotes
    }

    fun insert(note: Notas) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
    }

    fun updateById(title: String, content: String, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateById(title, content, id)
    }
}