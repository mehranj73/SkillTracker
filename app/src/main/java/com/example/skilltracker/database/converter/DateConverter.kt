package com.example.skilltracker.database.converter

import androidx.room.TypeConverter
import org.threeten.bp.*

import java.util.*

/**
 * I'm using org.threeten.bp.* for my localDateTime class because Java.time wants a higher base API
 * than I have. This means I have to use LocalDateTime instead of the Date class
 *
 */
class DateConverter {
    @TypeConverter
    fun dateToLong(date: LocalDateTime?): Long? {
        if (date != null) {
            val zonedDateTime = ZonedDateTime.of(date, ZoneId.systemDefault())
            return zonedDateTime.toInstant().toEpochMilli()
        }
        return null
    }

    @TypeConverter
    fun longToDate(timestamp: Long?) : LocalDateTime? {
        if (timestamp != null) {
            return LocalDateTime.ofInstant(
                timestamp.let { Instant.ofEpochMilli(it) },
                DateTimeUtils.toZoneId(TimeZone.getDefault())
            )
        }
        return null
    }
}