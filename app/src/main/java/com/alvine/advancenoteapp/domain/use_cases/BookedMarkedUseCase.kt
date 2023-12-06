package com.alvine.advancenoteapp.domain.use_cases

import com.alvine.advancenoteapp.data.local.model.Note
import com.alvine.advancenoteapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookedMarkedUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Note>>{
        return repository.getBookedMarkedNote()
    }
}