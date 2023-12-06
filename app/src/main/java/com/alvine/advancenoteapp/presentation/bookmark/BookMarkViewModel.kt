package com.alvine.advancenoteapp.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvine.advancenoteapp.data.local.model.Note
import com.alvine.advancenoteapp.domain.use_cases.BookedMarkedUseCase
import com.alvine.advancenoteapp.domain.use_cases.DeleteNoteUseCase
import com.alvine.advancenoteapp.domain.use_cases.UpdateNoteUseCase
import com.alvine.advancenoteapp.state.ScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookedMarkViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val bookedMarkedUseCase: BookedMarkedUseCase
): ViewModel(){
 private val _state: MutableStateFlow<BookMarkState> = MutableStateFlow(BookMarkState())
    val state:StateFlow<BookMarkState> =_state.asStateFlow()

    private fun getBookMarkNotes(){
        bookedMarkedUseCase().onEach {
            _state.value= BookMarkState(
                notes= ScreenViewState.Success(it)
            )
        }
            .catch {
                _state.value= BookMarkState(
                    notes = ScreenViewState.Error(it.message)
                )
            }
            .launchIn(viewModelScope)

    }

    fun onBookMarkChange(note: Note){
        viewModelScope.launch {
            updateNoteUseCase(
                note.copy(
                    isBookedMarked = !note.isBookedMarked
                )
            )
        }
    }

    fun deleteNote(noteId:Long){
        viewModelScope.launch {
            deleteNoteUseCase(
                noteId
            )
        }
    }

}

data class BookMarkState(
    val notes: ScreenViewState<List<Note>> =ScreenViewState.Loading
)