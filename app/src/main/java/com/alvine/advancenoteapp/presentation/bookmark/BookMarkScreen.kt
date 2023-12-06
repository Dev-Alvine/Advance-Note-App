package com.alvine.advancenoteapp.presentation.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alvine.advancenoteapp.data.local.model.Note
import com.alvine.advancenoteapp.presentation.home.NoteCard
import com.alvine.advancenoteapp.state.ScreenViewState

@Composable
fun BookMarkScreen(
    state: BookMarkState,
    onBookMarkChange:(note: Note) ->Unit,
    onDeleteNote:(Long) ->Unit,
    modifier: Modifier,
    onNoteClicked:(Long) ->Unit,
) {
   when(state.notes){
       is ScreenViewState.Loading->{
           CircularProgressIndicator()
       }
       is ScreenViewState.Success->{
           val notes= state.notes.data
           LazyColumn(
               contentPadding = PaddingValues(4.dp),
               modifier = modifier
           ){
               itemsIndexed(notes){index: Int, note: Note ->  
                   NoteCard(
                       index = index,
                       note = note,
                       onBookMarkChange = onBookMarkChange,
                       onNoteClicked = onNoteClicked,
                       onDeleteNote = onDeleteNote
                   )
               }
           }
       }
       is ScreenViewState.Error->{
           Text(
               text = state.notes.message ?: "Unknown Error",
               color = MaterialTheme.colorScheme.error
           )
       }
    }
}