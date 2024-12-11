package com.manyacov.data.rate_tracker.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates_table")
data class RateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "symbols")
    val symbols: String,
    @ColumnInfo(name = "value")
    val value: Double?,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)

