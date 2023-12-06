package com.alvine.advancenoteapp.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alvine.advancenoteapp.data.local.model.Note
import com.alvine.advancenoteapp.domain.use_cases.AddNoteUseCase
import com.alvine.advancenoteapp.domain.use_cases.GetNoteByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel @AssistedInject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    @Assisted private val noteId:Long

): ViewModel() {
    var state by mutableStateOf(DetailState())
        private set
    val isFormNotBlank:Boolean
        get() = state.title.isNotEmpty() && state.content.isNotEmpty()
    private val note:Note
        get() = state.run {
            Note(
                id,
                title,
                isBookMark,
                createdDate,
                content
            )
        }

    private fun initialize(){
        val isUpdatingNote=noteId != -1L
        state= state.copy(isUpdatingNote= isUpdatingNote)
         if(isUpdatingNote){
            getNoteById()
         }
    }

    fun onTitleChange(title: String){
        state= state.copy(title=title)
    }
    fun onContentChange(content: String){
        state= state.copy(content=content)
    }
    fun onBookMarkChang(isBookMark: Boolean){
        state= state.copy(isBookMark=isBookMark)
    }
    fun addOrUpdateNote()= viewModelScope.launch {
        addNoteUseCase(note = note)
    }




    private fun getNoteById()=viewModelScope.launch {
        getNoteByIdUseCase(noteId).collectLatest { note->
          state= state.copy(
              id= note.id,
              title = note.title,
              content = note.content,
              isBookMark =note.isBookedMarked,
              createdDate = note.createdDate

          )

        }
    }

}

 data class DetailState(
     val id:Long=0,
     val title:String="",
     val content :String="",
     val isBookMark:Boolean= false,
     val createdDate: Date =Date(),
     val isUpdatingNote:Boolean=false


 )

 class DetailViewModelFactory(
     private val noteId: Long,
     private val assistedFactory: DetailAssistedFactory
 ):ViewModelProvider.Factory{
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return assistedFactory.create(noteId) as T
     }
 }

@AssistedFactory
interface DetailAssistedFactory{
    fun create(noteId: Long):DetailViewModel
}