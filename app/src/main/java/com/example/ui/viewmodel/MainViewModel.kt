package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.BioCategory
import com.example.data.model.BioItem
import com.example.data.model.FavoriteEntity
import com.example.data.repository.BioRepository
import com.example.data.repository.FavoriteRepository
import com.example.data.util.NameStyleCategory
import com.example.data.util.StyledName
import com.example.data.util.StylishNameGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoriteRepository

    init {
        val db = AppDatabase.getInstance(application)
        repository = FavoriteRepository(db.favoriteDao())
    }

    // ==========================================
    // 1. Name Generator State
    // ==========================================
    private val _inputName = MutableStateFlow("Trick Master")
    val inputName: StateFlow<String> = _inputName.asStateFlow()

    private val _allGeneratedNames = MutableStateFlow(StylishNameGenerator.generateAllStyles("Trick Master"))

    private val _selectedNameCategory = MutableStateFlow(NameStyleCategory.ALL)
    val selectedNameCategory: StateFlow<NameStyleCategory> = _selectedNameCategory.asStateFlow()

    // Filtered styled names based on selected category
    val styledNames: StateFlow<List<StyledName>> = combine(_allGeneratedNames, _selectedNameCategory) { names, category ->
        if (category == NameStyleCategory.ALL) {
            names
        } else {
            names.filter { it.category == category }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _allGeneratedNames.value
    )

    // ==========================================
    // 2. Bio State (with Category & Search Filtering)
    // ==========================================
    // null means "All Categories"
    private val _selectedBioCategory = MutableStateFlow<BioCategory?>(BioCategory.ATTITUDE)
    val selectedBioCategory: StateFlow<BioCategory?> = _selectedBioCategory.asStateFlow()

    // Backward compatibility for existing references
    val selectedCategory: StateFlow<BioCategory> = _selectedBioCategory
        .map { it ?: BioCategory.ATTITUDE }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BioCategory.ATTITUDE
        )

    private val _bioSearchQuery = MutableStateFlow("")
    val bioSearchQuery: StateFlow<String> = _bioSearchQuery.asStateFlow()

    private val _allBios = MutableStateFlow(BioRepository.getAllBios())

    // Filtered bios based on Category + Keyword Search
    val biosList: StateFlow<List<BioItem>> = combine(_allBios, _selectedBioCategory, _bioSearchQuery) { bios, category, query ->
        var list = if (category == null) {
            bios
        } else {
            bios.filter { it.category == category }
        }

        val trimmedQuery = query.trim()
        if (trimmedQuery.isNotEmpty()) {
            list = list.filter { bio ->
                bio.text.contains(trimmedQuery, ignoreCase = true) ||
                bio.category.displayName.contains(trimmedQuery, ignoreCase = true)
            }
        }
        list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BioRepository.getBiosByCategory(BioCategory.ATTITUDE)
    )

    // ==========================================
    // 3. Favorites State from Room Database
    // ==========================================
    val allFavorites: StateFlow<List<FavoriteEntity>> = repository.allFavorites
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteContents: StateFlow<Set<String>> = repository.favoriteContents
        .map { it.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    // Favorites Screen Filter: "ALL", "NAME", "BIO"
    private val _favoritesFilter = MutableStateFlow("ALL")
    val favoritesFilter: StateFlow<String> = _favoritesFilter.asStateFlow()

    // ==========================================
    // Functions & Actions
    // ==========================================

    fun updateNameInput(newName: String) {
        _inputName.value = newName
        _allGeneratedNames.value = StylishNameGenerator.generateAllStyles(newName)
    }

    fun selectNameCategory(category: NameStyleCategory) {
        _selectedNameCategory.value = category
    }

    fun refreshOrRandomizeName() {
        val current = _inputName.value.trim()
        val nextName = if (current.isEmpty()) {
            "Trick Master"
        } else {
            val available = StylishNameGenerator.sampleNamePresets.filter { !it.equals(current, ignoreCase = true) }
            if (available.isNotEmpty()) available.random() else StylishNameGenerator.getRandomSampleName()
        }
        updateNameInput(nextName)
    }

    fun selectBioCategory(category: BioCategory?) {
        _selectedBioCategory.value = category
    }

    fun selectCategory(category: BioCategory) {
        selectBioCategory(category)
    }

    fun updateBioSearchQuery(query: String) {
        _bioSearchQuery.value = query
    }

    fun clearBioSearchQuery() {
        _bioSearchQuery.value = ""
    }

    fun setFavoritesFilter(filter: String) {
        _favoritesFilter.value = filter
    }

    fun toggleFavoriteName(styledName: StyledName) {
        val isFav = favoriteContents.value.contains(styledName.styledText)
        viewModelScope.launch {
            repository.toggleFavorite(
                type = "NAME",
                content = styledName.styledText,
                styleOrCategory = styledName.styleName,
                currentlyFavorite = isFav
            )
        }
    }

    fun toggleFavoriteBio(bio: BioItem) {
        val isFav = favoriteContents.value.contains(bio.text)
        viewModelScope.launch {
            repository.toggleFavorite(
                type = "BIO",
                content = bio.text,
                styleOrCategory = bio.category.displayName,
                currentlyFavorite = isFav
            )
        }
    }

    fun removeFavorite(content: String) {
        viewModelScope.launch {
            repository.removeFavorite(content)
        }
    }

    fun removeFavoriteById(id: Long) {
        viewModelScope.launch {
            repository.removeFavoriteById(id)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
