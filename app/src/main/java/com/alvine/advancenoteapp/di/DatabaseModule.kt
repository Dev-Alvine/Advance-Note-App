package com.alvine.advancenoteapp.di

import android.content.Context
import androidx.room.Room
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
    fun providesNoteDao(database: NoteDatabase): NoteDao {
        return database.noteDao
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase =Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "note_db"
    ).build()
}