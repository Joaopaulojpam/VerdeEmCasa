package br.edu.uninter.verdeemcasa.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ForestGreen = Color(0xFF2E6B47)
private val LightSage = Color(0xFFD7F0DF)
private val DeepForest = Color(0xFF123621)
private val WarmEarth = Color(0xFF5C6B57)
private val SoftCream = Color(0xFFF6FAF6)
private val CardSurface = Color(0xFFFFFFFF)
private val ErrorRed = Color(0xFFB3261E)

private val VerdeEmCasaColorScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = Color.White,
    primaryContainer = LightSage,
    onPrimaryContainer = DeepForest,
    secondary = WarmEarth,
    onSecondary = Color.White,
    background = SoftCream,
    onBackground = Color(0xFF1A221C),
    surface = CardSurface,
    onSurface = Color(0xFF1A221C),
    surfaceVariant = Color(0xFFE9F2EB),
    onSurfaceVariant = Color(0xFF424E45),
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun VerdeEmCasaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VerdeEmCasaColorScheme,
        content = content
    )
}
