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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.FavoriteEntity
import com.example.data.util.ClipboardHelper
import com.example.ui.components.NavigationTab
import com.example.ui.theme.TrickBackground
import com.example.ui.theme.TrickOutline
import com.example.ui.theme.TrickPrimary
import com.example.ui.theme.TrickSecondary
import com.example.ui.theme.TrickSurface
import com.example.ui.theme.TrickSurfaceVariant
import com.example.ui.theme.TrickTertiary
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun FavoritesScreen(
    viewModel: MainViewModel,
    onNavigateToTab: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val allFavorites by viewModel.allFavorites.collectAsStateWithLifecycle()
    val filter by viewModel.favoritesFilter.collectAsStateWithLifecycle()

    val filteredList = when (filter) {
        "NAME" -> allFavorites.filter { it.type == "NAME" }
        "BIO" -> allFavorites.filter { it.type == "BIO" }
        else -> allFavorites
    }

    val nameCount = allFavorites.count { it.type == "NAME" }
    val bioCount = allFavorites.count { it.type == "BIO" }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TrickBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Surface(
                color = TrickSurface,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = "Saved Favorites",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = Color.White
                    )
                    Text(
                        text = "Stored locally on your phone for offline access",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Segmented Filter Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterPill(
                            label = "All (${allFavorites.size})",
                            isSelected = filter == "ALL",
                            onClick = { viewModel.setFavoritesFilter("ALL") },
                            testTag = "fav_filter_all",
                            modifier = Modifier.weight(1f)
                        )
                        FilterPill(
                            label = "Names ($nameCount)",
                            isSelected = filter == "NAME",
                            onClick = { viewModel.setFavoritesFilter("NAME") },
                            testTag = "fav_filter_names",
                            modifier = Modifier.weight(1f)
                        )
                        FilterPill(
                            label = "Bios ($bioCount)",
                            isSelected = filter == "BIO",
                            onClick = { viewModel.setFavoritesFilter("BIO") },
                            testTag = "fav_filter_bios",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            if (filteredList.isEmpty()) {
                // Empty State
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = if (filter == "ALL") "No favorites saved yet"
                            else "No favorite ${if (filter == "NAME") "names" else "bios"} yet",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Tap the heart icon on any stylish name or bio to save it here.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 20.sp
                            ),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { onNavigateToTab(NavigationTab.NAME) },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Find Names")
                            }

                            Button(
                                onClick = { onNavigateToTab(NavigationTab.BIO) },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Explore Bios")
                            }
                        }
                    }
                }
            } else {
                // Favorites List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("favorites_list"),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(
                        items = filteredList,
                        key = { _, item -> item.id }
                    ) { index, item ->
                        FavoriteItemCard(
                            item = item,
                            onRemove = { viewModel.removeFavoriteById(item.id) },
                            onCopy = { ClipboardHelper.copyToClipboard(context, item.content, "Trick Master Favorite") },
                            onShare = { ClipboardHelper.shareText(context, item.content, "Share Favorite") },
                            testIndex = index
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else TrickSurfaceVariant,
        border = BorderStroke(1.dp, if (isSelected) TrickPrimary else TrickOutline.copy(alpha = 0.5f)),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier.padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 12.sp,
                    color = if (isSelected) TrickSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
private fun FavoriteItemCard(
    item: FavoriteEntity,
    onRemove: () -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit,
    testIndex: Int
) {
    val isName = item.type == "NAME"
    var isCopied by remember { mutableStateOf(false) }

    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(1500)
            isCopied = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("favorite_card_$testIndex"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TrickSurface),
        border = BorderStroke(1.dp, TrickOutline.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Badges & Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Type Badge ("NAME" or "BIO")
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (isName) TrickPrimary.copy(alpha = 0.25f)
                                else TrickSecondary.copy(alpha = 0.25f)
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = if (isName) "NAME" else "BIO",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isName) TrickPrimary else TrickSecondary,
                                fontSize = 10.sp
                            )
                        )
                    }

                    // Style/Category Badge
                    if (item.styleOrCategory.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = item.styleOrCategory,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = isCopied,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF10B981).copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, Color(0xFF10B981)),
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Text(
                                text = "Copied! ✓",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF34D399),
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                // Actions: Copy, Share, Remove
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalIconButton(
                        onClick = {
                            onCopy()
                            isCopied = true
                        },
                        modifier = Modifier.size(36.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (isCopied) Color(0xFF10B981).copy(alpha = 0.25f) else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isCopied) Color(0xFF34D399) else MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = if (isCopied) "Copied" else "Copy text",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    FilledTonalIconButton(
                        onClick = onShare,
                        modifier = Modifier.size(36.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share text",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    FilledTonalIconButton(
                        onClick = onRemove,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("remove_favorite_$testIndex"),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                            contentColor = TrickTertiary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove favorite",
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Saved content text
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
            ) {
                Text(
                    text = item.content,
                    style = if (isName) {
                        MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            letterSpacing = 0.5.sp
                        )
                    } else {
                        MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            lineHeight = 21.sp
                        )
                    },
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }
    }
}
