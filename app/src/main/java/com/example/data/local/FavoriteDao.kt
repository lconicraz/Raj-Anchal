package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites ORDER BY createdAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE type = :type ORDER BY createdAt DESC")
    fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE content = :content LIMIT 1)")
    fun isFavorite(content: String): Flow<Boolean>

    @Query("SELECT content FROM favorites")
    fun getAllFavoriteContents(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FavoriteEntity): Long

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM favorites WHERE content = :content")
    suspend fun deleteByContent(content: String)

    @Query("DELETE FROM favorites")
    suspend fun deleteAll()
}
