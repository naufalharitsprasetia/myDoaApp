package com.example.mydoa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {
    val notes: StateFlow<List<Note>> = noteDao.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun deleteNoteById(id: Int) {
        viewModelScope.launch {
            noteDao.deleteById(id)
        }
    }

    fun updateNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            noteDao.update(id, title, content)
        }
    }
}
