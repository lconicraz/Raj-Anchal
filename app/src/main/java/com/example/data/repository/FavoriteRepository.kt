package com.example.data.repository

import com.example.data.local.FavoriteDao
import com.example.data.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val dao: FavoriteDao) {

    val allFavorites: Flow<List<FavoriteEntity>> = dao.getAllFavorites()
    val favoriteContents: Flow<List<String>> = dao.getAllFavoriteContents()

    fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>> {
        return dao.getFavoritesByType(type)
    }

    fun isFavorite(content: String): Flow<Boolean> {
        return dao.isFavorite(content)
    }

    suspend fun addFavorite(type: String, content: String, styleOrCategory: String): Long {
        val entity = FavoriteEntity(
            type = type,
            content = content,
            styleOrCategory = styleOrCategory
        )
        return dao.insert(entity)
    }

    suspend fun removeFavorite(content: String) {
        dao.deleteByContent(content)
    }

    suspend fun removeFavoriteById(id: Long) {
        dao.deleteById(id)
    }

    suspend fun toggleFavorite(type: String, content: String, styleOrCategory: String, currentlyFavorite: Boolean) {
        if (currentlyFavorite) {
            dao.deleteByContent(content)
        } else {
            addFavorite(type, content, styleOrCategory)
        }
    }
}
