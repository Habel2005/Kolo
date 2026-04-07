package com.kolo.prayer.ui.bookdetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolo.prayer.ui.components.*
import com.kolo.prayer.ui.theme.*

@Composable
fun BookDetailScreen(
    onBack: () -> Unit,
    onSectionClick: (bookId: String, sectionId: String) -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val summary = uiState.summary ?: return
    val book = uiState.book ?: return

    val coverColor = try {
        Color(android.graphics.Color.parseColor(summary.colorHex))
    } catch (_: Exception) { Maroon }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Maroon Header ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Maroon)
                .statusBarsPadding()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            // Back button
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.15f))
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp),
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Cover thumbnail
            Box(
                modifier = Modifier
                    .size(width = 54.dp, height = 68.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.15f))
                    .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = summary.symbol,
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NotoSerifMalayalam,
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Meta
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = summary.titleMl,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = NotoSerifMalayalam,
                        color = Color.White,
                        lineHeight = 22.sp,
                    ),
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "${summary.titleEn} · ${summary.subtitle}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.6f),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    DetailTag(if (summary.isDaily) "Daily" else summary.category.replace("_", " ").replaceFirstChar { it.uppercase() })
                    DetailTag("${summary.sectionCount} Sections")
                    DetailTag("Malayalam")
                }
            }
        }

        // ── Continue Banner ──
        val currentSection = book.sections.find { it.id == uiState.currentSectionId }
        if (currentSection != null) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable { onSectionClick(book.id, currentSection.id) }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Maroon),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Continue",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp),
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Continue from",
                        style = MaterialTheme.typography.labelSmall,
                        color = KoloThemeExtras.colors.inkFaint,
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = "${currentSection.sortOrder}. ${currentSection.titleEn} · ${uiState.currentProgress}% complete",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = NotoSerifMalayalam,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                        ),
                        color = Ink,
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go",
                    tint = Maroon,
                    modifier = Modifier.size(16.dp),
                )
            }
        }

        // ── Table of Contents ──
        Column(modifier = Modifier.padding(horizontal = 14.dp)) {
            uiState.sectionsByCategory.forEach { (category, sections) ->
                TocSectionHeader(label = category)
                sections.forEach { section ->
                    TocItem(
                        number = section.sortOrder,
                        titleMl = section.titleMl,
                        titleEn = section.titleEn,
                        isCurrent = section.id == uiState.currentSectionId,
                        isCompleted = section.id in uiState.completedSections,
                        progressPercent = if (section.id == uiState.currentSectionId) uiState.currentProgress else null,
                        onClick = { onSectionClick(book.id, section.id) },
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun DetailTag(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .background(
                Color.White.copy(alpha = 0.18f),
                RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 7.dp, vertical = 3.dp),
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
        color = Color.White.copy(alpha = 0.85f),
    )
}
