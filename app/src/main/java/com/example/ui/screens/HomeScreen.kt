package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.OfflineBolt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.util.ClipboardHelper
import com.example.ui.components.NavigationTab
import com.example.ui.theme.AttitudeEnergyGradient
import com.example.ui.theme.CyberVioletGradient
import com.example.ui.theme.GoldenRoyalGradient
import com.example.ui.theme.NeonCyanGradient
import com.example.ui.theme.RomanticLoveGradient
import com.example.ui.theme.TrickBackground
import com.example.ui.theme.TrickOutline
import com.example.ui.theme.TrickPrimary
import com.example.ui.theme.TrickSecondary
import com.example.ui.theme.TrickSurface
import com.example.ui.theme.TrickSurfaceVariant
import com.example.ui.theme.TrickTertiary

@Composable
fun HomeScreen(
    onNavigateToTab: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TrickBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Clean Logo / Icon Hero Area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = TrickSurface),
                border = BorderStroke(1.dp, TrickOutline.copy(alpha = 0.6f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Hero Image Banner Background
                    Image(
                        painter = painterResource(id = R.drawable.hero_banner),
                        contentDescription = "Trick Master Hero Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Scrim gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        TrickSurface.copy(alpha = 0.85f),
                                        TrickSurface
                                    )
                                )
                            )
                    )

                    // Logo & App Title Content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Spacer(modifier = Modifier.height(30.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Circular Emblem Logo
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(TrickPrimary, TrickSecondary)
                                        )
                                    )
                                    .padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_trick_master_img),
                                    contentDescription = "Trick Master Logo",
                                    modifier = Modifier
                                        .size(52.dp)
                                        .clip(RoundedCornerShape(14.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = "Trick Master",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 26.sp,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = Color.White
                                )
                                Text(
                                    text = "Stylish Names & Aesthetic Bios",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TrickSecondary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Quick info chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            QuickInfoChip(icon = Icons.Default.AutoAwesome, text = "100+ Styles")
                            QuickInfoChip(icon = Icons.Default.Bookmark, text = "300+ Bios")
                            QuickInfoChip(icon = Icons.Default.OfflineBolt, text = "100% Offline")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Explore Features",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Large Card 1: "Stylish Name"
            LargeFeatureCard(
                title = "Stylish Name",
                tagline = "Unicode & VIP Gaming Styler",
                description = "Type any name to instantly generate 100+ stylish Unicode fonts, VIP symbols, aesthetic and gamer nicknames.",
                gradient = CyberVioletGradient,
                icon = Icons.Default.Edit,
                buttonText = "Generate Names",
                onClick = { onNavigateToTab(NavigationTab.NAME) },
                testTag = "home_card_name"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Large Card 2: "Stylish Bio"
            LargeFeatureCard(
                title = "Stylish Bio",
                tagline = "Ready-made Social Quotes",
                description = "300+ curated bios across Attitude, Love, Royal, Sad, Instagram, Savage, Cute, Motivational, Gaming & Aesthetic with live search.",
                gradient = GoldenRoyalGradient,
                icon = Icons.Default.Bookmark,
                buttonText = "Browse Bios",
                onClick = { onNavigateToTab(NavigationTab.BIO) },
                testTag = "home_card_bio"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Quick Featured Trick Card
            FeaturedTrickCard(context = context)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun QuickInfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Surface(
        color = TrickSurfaceVariant.copy(alpha = 0.7f),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, TrickOutline.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TrickPrimary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun LargeFeatureCard(
    title: String,
    tagline: String,
    description: String,
    gradient: Brush,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    buttonText: String,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = TrickSurface),
        border = BorderStroke(1.dp, TrickOutline.copy(alpha = 0.6f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tagline,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = TrickSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                // Glowing circular icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(gradient),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp,
                    fontSize = 13.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = buttonText,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FeaturedTrickCard(context: android.content.Context) {
    val sampleStyle = "꧁༺ 𝕿𝖗𝖎𝖈𝖐 𝕸𝖆𝖘𝖙𝖊𝖗 ༻꧂"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("featured_trick_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = TrickSurfaceVariant),
        border = BorderStroke(1.dp, TrickOutline.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = TrickTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "VIP Featured Style",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TrickTertiary
                        )
                    )
                }

                FilledTonalIconButton(
                    onClick = {
                        ClipboardHelper.copyToClipboard(context, sampleStyle, "Trick Master Featured")
                    },
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy featured style",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = TrickBackground.copy(alpha = 0.6f)
            ) {
                Text(
                    text = sampleStyle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }
    }
}
