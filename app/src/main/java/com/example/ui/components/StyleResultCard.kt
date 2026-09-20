package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.util.ClipboardHelper
import com.example.data.util.NameStyleCategory
import com.example.data.util.StyledName
import com.example.ui.theme.CyberVioletGradient
import com.example.ui.theme.GoldenRoyalGradient
import com.example.ui.theme.GothicMidnightGradient
import com.example.ui.theme.NeonCyanGradient
import com.example.ui.theme.RomanticLoveGradient
import com.example.ui.theme.SavageFireGradient
import com.example.ui.theme.SimpleCleanGradient
import com.example.ui.theme.TrickOutline
import com.example.ui.theme.TrickSecondary
import com.example.ui.theme.TrickSurface
import com.example.ui.theme.TrickTertiary
import kotlinx.coroutines.delay

fun getNameCategoryGradient(category: NameStyleCategory): Brush {
    return when (category) {
        NameStyleCategory.ALL -> CyberVioletGradient
        NameStyleCategory.GAMER -> SavageFireGradient
        NameStyleCategory.ROYAL -> GoldenRoyalGradient
        NameStyleCategory.AESTHETIC -> RomanticLoveGradient
        NameStyleCategory.GOTHIC -> GothicMidnightGradient
        NameStyleCategory.CLASSIC -> NeonCyanGradient
        NameStyleCategory.DECORATIVE -> SimpleCleanGradient
    }
}

@Composable
fun StyleResultCard(
    item: StyledName,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    testIndex: Int = 0
) {
    val context = LocalContext.current
    var isCopied by remember { mutableStateOf(false) }

    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(1500)
            isCopied = false
        }
    }

    val favColor by animateColorAsState(
        targetValue = if (isFavorite) TrickTertiary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        label = "fav_color"
    )
    val favScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.15f else 1.0f,
        animationSpec = spring(),
        label = "fav_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("name_card_$testIndex"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = TrickSurface
        ),
        border = BorderStroke(1.dp, TrickOutline.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Style Category Badge & Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gradient Style Tag Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(getNameCategoryGradient(item.category))
                            .padding(horizontal = 9.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = item.category.displayName,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        )
                    }

                    Text(
                        text = item.styleName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = TrickSecondary,
                            fontSize = 12.sp
                        )
                    )

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

                // Action buttons: Copy, Share, Favorite
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Copy button
                    FilledTonalIconButton(
                        onClick = {
                            ClipboardHelper.copyToClipboard(context, item.styledText, "Trick Master Name")
                            isCopied = true
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("copy_name_$testIndex"),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (isCopied) Color(0xFF10B981).copy(alpha = 0.25f) else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isCopied) Color(0xFF34D399) else MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = if (isCopied) "Copied" else "Copy styled name",
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Share button
                    FilledTonalIconButton(
                        onClick = {
                            ClipboardHelper.shareText(context, item.styledText, "Share Stylish Name")
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("share_name_$testIndex"),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share styled name",
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Favorite button
                    FilledTonalIconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(38.dp)
                            .scale(favScale)
                            .testTag("fav_name_$testIndex"),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (isFavorite) MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f)
                            else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = favColor
                        )
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // The styled name text container
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
            ) {
                Text(
                    text = item.styledText,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 19.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                        .testTag("styled_text_$testIndex")
                )
            }
        }
    }
}
