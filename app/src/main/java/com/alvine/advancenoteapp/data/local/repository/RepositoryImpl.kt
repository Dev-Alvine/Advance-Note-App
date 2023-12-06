package com.alvine.advancenoteapp.data.local.repository

import com.alvine.advancenoteapp.data.local.model.Note
import com.alvine.advancenoteapp.data.local.model.NoteDao
import com.alvine.advancenoteapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): Repository
{
    override fun getAllNotes(): Flow<List<Note>> {
       return noteDao.getAllNotes()
    }

    override fun getNoteById(id: Long): Flow<Note> {
        return noteDao.getNoteById(id)
    }

    override fun getBookedMarkedNote(): Flow<List<Note>> {
        return noteDao.getBookedMarkedNote()
    }

    override suspend fun updateNote(note: Note) {
       noteDao.updateNote(note)
    }

    override suspend fun deleteNote(id: Long) {
       noteDao.deleteNote(id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }
}