package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.BioCategory
import com.example.data.repository.BioRepository
import com.example.ui.components.BioCard
import com.example.ui.components.getCategoryGradient
import com.example.ui.theme.CyberVioletGradient
import com.example.ui.theme.TrickBackground
import com.example.ui.theme.TrickOutline
import com.example.ui.theme.TrickPrimary
import com.example.ui.theme.TrickSecondary
import com.example.ui.theme.TrickSurface
import com.example.ui.theme.TrickSurfaceVariant
import com.example.ui.viewmodel.MainViewModel

fun getCategoryIcon(category: BioCategory): ImageVector {
    return when (category) {
        BioCategory.ATTITUDE -> Icons.Default.FlashOn
        BioCategory.LOVE -> Icons.Default.Favorite
        BioCategory.ROYAL -> Icons.Default.MilitaryTech
        BioCategory.SAD -> Icons.Default.SentimentDissatisfied
        BioCategory.INSTAGRAM -> Icons.Default.CameraAlt
        BioCategory.SAVAGE -> Icons.Default.Whatshot
        BioCategory.CUTE -> Icons.Default.AutoAwesome
        BioCategory.MOTIVATIONAL -> Icons.AutoMirrored.Filled.TrendingUp
        BioCategory.FRIENDSHIP -> Icons.Default.People
        BioCategory.GAMING -> Icons.Default.SportsEsports
        BioCategory.AESTHETIC -> Icons.Default.Palette
        BioCategory.SIMPLE -> Icons.Default.Grass
    }
}

@Composable
fun BioScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val selectedBioCategory by viewModel.selectedBioCategory.collectAsStateWithLifecycle()
    val biosList by viewModel.biosList.collectAsStateWithLifecycle()
    val favoriteContents by viewModel.favoriteContents.collectAsStateWithLifecycle()
    val searchQuery by viewModel.bioSearchQuery.collectAsStateWithLifecycle()
    val allCategories = BioRepository.getAllCategories()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TrickBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header & Search Bar & Category Chips Bar
            Surface(
                color = TrickSurface,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp, bottom = 12.dp)
                ) {
                    // Title Area
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = "Stylish Bio Catalog",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = Color.White
                        )
                        Text(
                            text = "300+ ready-made bios for Instagram, WhatsApp & Gaming",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.updateBioSearchQuery(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .testTag("bio_search_input"),
                        placeholder = {
                            Text(
                                "Search 300+ bios (e.g. king, dream, love, clutch)...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = TrickSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = searchQuery.isNotEmpty(),
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                IconButton(
                                    onClick = {
                                        viewModel.clearBioSearchQuery()
                                        focusManager.clearFocus()
                                    },
                                    modifier = Modifier.testTag("bio_search_clear_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear search",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = TrickSurfaceVariant,
                            unfocusedContainerColor = TrickSurfaceVariant,
                            focusedBorderColor = TrickPrimary,
                            unfocusedBorderColor = TrickOutline.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = TrickPrimary
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Horizontal Category Chips (Includes "All Categories" chip)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        // "All" chip
                        item {
                            val isAllSelected = selectedBioCategory == null
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (isAllSelected) MaterialTheme.colorScheme.primaryContainer else TrickSurfaceVariant,
                                border = BorderStroke(
                                    1.dp,
                                    if (isAllSelected) TrickPrimary else TrickOutline.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { viewModel.selectBioCategory(null) }
                                    .testTag("category_chip_ALL")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clip(CircleShape)
                                            .background(CyberVioletGradient),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "All Categories",
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isAllSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }
                        }

                        // Category items
                        items(allCategories) { category ->
                            val isSelected = selectedBioCategory == category
                            val categoryGradient = getCategoryGradient(category)

                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else TrickSurfaceVariant,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) TrickPrimary else TrickOutline.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { viewModel.selectBioCategory(category) }
                                    .testTag("category_chip_${category.name}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clip(CircleShape)
                                            .background(categoryGradient),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = getCategoryIcon(category),
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = category.displayName,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Category / Search Summary Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    val titleText = when {
                        searchQuery.isNotBlank() && selectedBioCategory != null ->
                            "${selectedBioCategory!!.displayName} matching \"$searchQuery\" (${biosList.size})"
                        searchQuery.isNotBlank() ->
                            "Results for \"$searchQuery\" (${biosList.size})"
                        selectedBioCategory != null ->
                            "${selectedBioCategory!!.displayName} Bios (${biosList.size})"
                        else ->
                            "All Bios (${biosList.size})"
                    }

                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = selectedBioCategory?.description ?: "Showing curated bios from all 12 categories",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            // Empty State if no bios match
            if (biosList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = TrickSecondary.copy(alpha = 0.6f),
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "No bios found matching \"$searchQuery\"",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Try searching for another keyword or clear the search filter.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            onClick = { viewModel.clearBioSearchQuery() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TrickPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Clear Search Filter")
                        }
                    }
                }
            } else {
                // Bios List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("bios_list"),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(
                        items = biosList,
                        key = { _, item -> item.id }
                    ) { index, bio ->
                        val isFav = favoriteContents.contains(bio.text)
                        BioCard(
                            bio = bio,
                            isFavorite = isFav,
                            onToggleFavorite = { viewModel.toggleFavoriteBio(bio) },
                            testIndex = index
                        )
                    }
                }
            }
        }
    }
}
