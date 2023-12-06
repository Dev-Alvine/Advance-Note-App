package com.alvine.advancenoteapp.di

import android.content.Context
import androidx.room.Room
import com.alvine.advancenoteapp.data.local.model.Note
import com.alvine.advancenoteapp.data.local.model.NoteDao
import com.alvine.advancenoteapp.data.local.model.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesNoteDao(dao:NoteDatabase):NoteDao{
        return dao.noteDao
    }

    @Provides
    @Singleton
    fun providesNoteDatabase(
        @ApplicationContext context: Context
    ):NoteDatabase =Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "notes_db"
    )
        .build()
}