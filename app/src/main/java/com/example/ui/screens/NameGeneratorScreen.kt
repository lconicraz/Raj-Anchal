package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.util.NameStyleCategory
import com.example.data.util.StylishNameGenerator
import com.example.ui.components.StyleResultCard
import com.example.ui.components.getNameCategoryGradient
import com.example.ui.theme.TrickBackground
import com.example.ui.theme.TrickOutline
import com.example.ui.theme.TrickPrimary
import com.example.ui.theme.TrickSecondary
import com.example.ui.theme.TrickSurface
import com.example.ui.theme.TrickSurfaceVariant
import com.example.ui.viewmodel.MainViewModel

fun getNameCategoryIcon(category: NameStyleCategory): ImageVector {
    return when (category) {
        NameStyleCategory.ALL -> Icons.Default.AutoAwesome
        NameStyleCategory.GAMER -> Icons.Default.SportsEsports
        NameStyleCategory.ROYAL -> Icons.Default.MilitaryTech
        NameStyleCategory.AESTHETIC -> Icons.Default.Favorite
        NameStyleCategory.GOTHIC -> Icons.Default.Security
        NameStyleCategory.CLASSIC -> Icons.Default.TextFormat
        NameStyleCategory.DECORATIVE -> Icons.Default.FlashOn
    }
}

@Composable
fun NameGeneratorScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val inputName by viewModel.inputName.collectAsStateWithLifecycle()
    val styledNames by viewModel.styledNames.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedNameCategory.collectAsStateWithLifecycle()
    val favoriteContents by viewModel.favoriteContents.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    var rotationDegrees by remember { mutableFloatStateOf(0f) }
    val animatedRotation by animateFloatAsState(
        targetValue = rotationDegrees,
        animationSpec = tween(durationMillis = 400, easing = LinearEasing),
        label = "refresh_rotation"
    )

    val quickSampleNames = listOf("Trick Master", "Shadow", "Ghost", "King", "Legend", "Queen", "Phoenix", "Sniper", "Viper", "Valkyrie")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TrickBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header & Input Section matching the aesthetic of BioScreen
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
                    // Title, Subtitle, and Refresh / Regenerate Action
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Stylish Name Generator",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
                                color = Color.White
                            )
                            Text(
                                text = "100+ unique Unicode, VIP, Gothic & Gaming styles",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }

                        // Regenerate / Refresh Button
                        FilledTonalIconButton(
                            onClick = {
                                rotationDegrees += 360f
                                viewModel.refreshOrRandomizeName()
                                focusManager.clearFocus()
                            },
                            modifier = Modifier
                                .size(42.dp)
                                .testTag("regenerate_name_btn"),
                            colors = IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = TrickSecondary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Regenerate / Refresh random name",
                                modifier = Modifier
                                    .size(22.dp)
                                    .rotate(animatedRotation)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Text Input Field at top
                    OutlinedTextField(
                        value = inputName,
                        onValueChange = { viewModel.updateNameInput(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .testTag("name_input_field"),
                        placeholder = { Text("Type your name here...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = TrickPrimary
                            )
                        },
                        trailingIcon = {
                            if (inputName.isNotEmpty()) {
                                IconButton(
                                    onClick = { viewModel.updateNameInput("") },
                                    modifier = Modifier.testTag("clear_name_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear input",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TrickPrimary,
                            unfocusedBorderColor = TrickOutline,
                            focusedContainerColor = TrickSurfaceVariant.copy(alpha = 0.5f),
                            unfocusedContainerColor = TrickSurfaceVariant.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = TrickPrimary
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Category Filter Chips Bar
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(NameStyleCategory.entries) { category ->
                            val isSelected = selectedCategory == category
                            val categoryGradient = getNameCategoryGradient(category)

                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else TrickSurfaceVariant,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) TrickPrimary else TrickOutline.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { viewModel.selectNameCategory(category) }
                                    .testTag("name_category_chip_${category.name}")
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
                                            imageVector = getNameCategoryIcon(category),
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

                    Spacer(modifier = Modifier.height(10.dp))

                    // Quick Sample Tags Row
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(quickSampleNames) { sample ->
                            val isSelected = inputName.equals(sample, ignoreCase = true)
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f) else TrickSurfaceVariant.copy(alpha = 0.6f),
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) TrickPrimary.copy(alpha = 0.7f) else TrickOutline.copy(alpha = 0.35f)
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        viewModel.updateNameInput(sample)
                                        focusManager.clearFocus()
                                    }
                                    .testTag("quick_sample_$sample")
                            ) {
                                Text(
                                    text = sample,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = if (isSelected) TrickSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Category Summary Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${selectedCategory.displayName} (${styledNames.size} styles)",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = selectedCategory.description,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            // Results List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("styled_names_list"),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(
                    items = styledNames,
                    key = { _, item -> "${item.styleName}_${item.styledText}" }
                ) { index, item ->
                    val isFav = favoriteContents.contains(item.styledText)
                    StyleResultCard(
                        item = item,
                        isFavorite = isFav,
                        onToggleFavorite = { viewModel.toggleFavoriteName(item) },
                        testIndex = index
                    )
                }
            }
        }
    }
}
