package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val TrickMasterColorScheme = darkColorScheme(
    primary = TrickPrimary,
    onPrimary = TrickOnPrimary,
    primaryContainer = TrickPrimaryContainer,
    onPrimaryContainer = TrickOnPrimaryContainer,
    secondary = TrickSecondary,
    onSecondary = TrickOnSecondary,
    secondaryContainer = TrickSecondaryContainer,
    onSecondaryContainer = TrickOnSecondaryContainer,
    tertiary = TrickTertiary,
    onTertiary = TrickOnTertiary,
    tertiaryContainer = TrickTertiaryContainer,
    onTertiaryContainer = TrickOnTertiaryContainer,
    background = TrickBackground,
    onBackground = TrickOnBackground,
    surface = TrickSurface,
    onSurface = TrickOnSurface,
    surfaceVariant = TrickSurfaceVariant,
    onSurfaceVariant = TrickOnSurfaceVariant,
    surfaceContainerHigh = TrickSurfaceContainerHigh,
    outline = TrickOutline,
    outlineVariant = TrickOutlineVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to true as user explicitly requested a dark-themed app
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TrickMasterColorScheme,
        typography = Typography,
        content = content
    )
}
