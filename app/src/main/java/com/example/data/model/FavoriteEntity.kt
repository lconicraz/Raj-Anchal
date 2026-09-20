package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for locally saved favorite names and bios in Trick Master.
 */
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String, // "NAME" or "BIO"
    val content: String,
    val styleOrCategory: String,
    val createdAt: Long = System.currentTimeMillis()
)
