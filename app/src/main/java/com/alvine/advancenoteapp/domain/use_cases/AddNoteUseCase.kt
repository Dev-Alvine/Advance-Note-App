package com.alvine.advancenoteapp.domain.use_cases

import com.alvine.advancenoteapp.data.local.model.Note
import com.alvine.advancenoteapp.domain.repository.Repository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private  val repository: Repository
) {
    suspend operator fun invoke(note: Note) = repository.insertNote(note)
}