package com.alvine.advancenoteapp.domain.use_cases

import com.alvine.advancenoteapp.domain.repository.Repository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id:Long)= repository.deleteNote(id)
}