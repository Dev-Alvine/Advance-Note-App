package com.alvine.advancenoteapp.data.local.converter

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toDate(date:Long?): Date?{
        return date?.let { Date(it) }

    }
    @TypeConverter
    fun fromDate(date:Date?): Long?{
        return date?.time

    }
}