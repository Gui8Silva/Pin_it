package com.example.pin_it.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")

data class Notas (

    @PrimaryKey(autoGenerate = true) val id: Int? = null,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "content") val content: String
)