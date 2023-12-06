package com.alvine.advancenoteapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val title:String,
    val isBookedMarked:Boolean= false,
    val createdDate:Date,
    val content:String,
)