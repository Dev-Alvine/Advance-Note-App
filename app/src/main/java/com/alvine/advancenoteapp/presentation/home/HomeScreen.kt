package com.alvine.advancenoteapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alvine.advancenoteapp.data.local.model.Note
import com.alvine.advancenoteapp.state.ScreenViewState

@Composable
fun HomeScreen(
    modifier: Modifier,
    state: HomeState,
    onBookMarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    when(state.notes){
        is ScreenViewState.Loading-> {
            CircularProgressIndicator()
        }
        is ScreenViewState.Success->{
            val notes= state.notes.data
            HomeDetail(
                notes =notes ,
                onBookMarkChange =onBookMarkChange ,
                onNoteClicked = onNoteClicked,
                onDeleteNote = onDeleteNote,
                modifier = modifier
            )
        }

        is ScreenViewState.Error->{
            Text(text = state.notes.message?:"Unknown Error", color = MaterialTheme.colorScheme.error)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeDetail(
    notes:List<Note>,
    onBookMarkChange:(note:Note) -> Unit,
    onNoteClicked:(Long) -> Unit,
    onDeleteNote:(Long) -> Unit,
    modifier:Modifier
){
    LazyVerticalStaggeredGrid(
    columns = StaggeredGridCells.Fixed(2),
    contentPadding = PaddingValues(4.dp),
    modifier = modifier
    ){
        itemsIndexed(notes){ index, note ->
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    index:Int,
    note: Note,
    onBookMarkChange:(note:Note) -> Unit,
    onNoteClicked:(Long) -> Unit,
    onDeleteNote:(Long) -> Unit,

) {
    val isEvenIndex= index % 2 == 0
    val shape = when{
        isEvenIndex->{
            RoundedCornerShape(
                topStart = 50f,
                bottomEnd = 50f
            )
        }
        else->{
            RoundedCornerShape(
                    topEnd = 50f,
                    bottomStart = 50f
                )

        }

    }
    val icon= if (note.isBookedMarked) Icons.Default.Star
    else Icons.Outlined.Star

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape =shape,
        onClick = {onNoteClicked(note.id)}
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {onDeleteNote(note.id)}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)

                    IconButton(onClick = {onBookMarkChange(note)}) {
                    Icon(imageVector = icon, contentDescription = null)

                }
            }
        }
    }
}
}