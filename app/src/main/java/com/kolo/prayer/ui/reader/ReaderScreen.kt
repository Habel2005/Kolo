package com.kolo.prayer.ui.reader

import android.app.Activity
import android.view.WindowManager
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolo.prayer.ui.components.BookmarkToast
import com.kolo.prayer.ui.theme.*

@Composable
fun ReaderScreen(
    onBack: () -> Unit,
    viewModel: ReaderViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val section = uiState.currentSection ?: return
    val book = uiState.book ?: return

    val readerBg = when (uiState.theme) {
        "sepia" -> SepiaBackground
        "night" -> NightBackground
        else -> DayBackground
    }
    val readerText = when (uiState.theme) {
        "sepia" -> SepiaOnBackground
        "night" -> NightOnBackground
        else -> Ink
    }
    val readerFaint = when (uiState.theme) {
        "night" -> NightOnSurfaceVariant
        else -> InkFaint
    }
    
    val highlightColor = when (uiState.theme) {
        "night" -> Gold.copy(alpha = 0.8f)
        else -> Maroon
    }

    val context = LocalContext.current
    DisposableEffect(uiState.keepScreenOn) {
        val window = (context as? Activity)?.window
        if (uiState.keepScreenOn) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    val listState = rememberLazyListState()
    LaunchedEffect(uiState.currentSectionIndex) {
        listState.scrollToItem(0)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(readerBg)
        ) {
            // ── Top Bar ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Back
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(readerText.copy(alpha = 0.08f))
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = readerText.copy(alpha = 0.6f),
                        modifier = Modifier.size(14.dp),
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Breadcrumb
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.titleEn,
                        style = MaterialTheme.typography.labelSmall,
                        color = readerFaint,
                    )
                    Text(
                        text = "${section.sortOrder}. ${section.titleEn}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = NotoSerifMalayalam,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                        ),
                        color = readerText,
                    )
                }

                // Menu trigger
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(readerText.copy(alpha = 0.08f))
                        .clickable { viewModel.toggleDrawer() },
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .height(1.5.dp)
                                    .background(readerText.copy(alpha = 0.5f), RoundedCornerShape(1.dp))
                            )
                        }
                    }
                }
            }

            // ── Chapter Title Block ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "SECTION ${section.sortOrder} OF ${uiState.totalSections}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.sp,
                    ),
                    color = readerFaint,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = section.titleMl,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = NotoSerifMalayalam,
                        color = Maroon,
                    ),
                )
                if (section.titleEn.isNotEmpty()) {
                    Text(
                        text = section.titleEn,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = NotoSerifMalayalam,
                            fontSize = 12.sp,
                        ),
                        color = readerFaint,
                    )
                }
            }

            // Divider
            HorizontalDivider(color = KoloThemeExtras.colors.border)

            // ── Ornament ──
            Text(
                text = "✦  ✦  ✦",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Gold,
                letterSpacing = 4.sp,
            )

            val isChantContext = remember(section.verses) {
                val flags = BooleanArray(section.verses.size)
                var inChant = false
                for (i in section.verses.indices) {
                    val v = section.verses[i]
                    if (v.speaker == "chant" || v.type == "verse") {
                        inChant = true
                        flags[i] = true
                        continue
                    }
                    if (v.speaker == "sub_heading" || v.type == "ui_element" || v.type == "instruction" || v.speaker == "rubric") {
                        inChant = false
                        flags[i] = false
                        continue
                    }
                    if (inChant) {
                        val isHallelujah = v.textMl.contains("ഹല്ലേലുയ്യ") || v.textMl.contains("ഹല്ലേലുയ") || v.textEn.contains("Hallelujah", ignoreCase = true)
                        if (isHallelujah) {
                            inChant = false
                            flags[i] = false
                        } else {
                            val nextIsChant = if (i + 1 < section.verses.size) {
                                val nextV = section.verses[i + 1]
                                nextV.speaker == "chant" || nextV.type == "verse" || 
                                nextV.speaker == "sub_heading" || nextV.type == "ui_element" || 
                                nextV.type == "instruction" || nextV.speaker == "rubric"
                            } else true
                            
                            if (nextIsChant) {
                                inChant = false
                                flags[i] = false
                            } else {
                                flags[i] = true
                            }
                        }
                    } else {
                        flags[i] = false
                    }
                }
                flags.toList()
            }

            // ── Reading Body ──
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 22.dp),
                contentPadding = PaddingValues(bottom = 40.dp),
            ) {

                // Verses
                itemsIndexed(section.verses) { index, verse ->
                    val isHighlighted = verse.verseNum == uiState.highlightedVerseNum

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp)
                            .then(
                                if (isHighlighted) {
                                    Modifier
                                        .background(
                                            Gold.copy(alpha = 0.12f),
                                            RoundedCornerShape(4.dp),
                                        )
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                } else {
                                    Modifier
                                }
                            )
                            .pointerInput(verse.verseNum) {
                                detectTapGestures(
                                    onLongPress = {
                                        viewModel.onLongPressVerse(verse.verseNum)
                                    }
                                )
                            }
                    ) {
                        if (verse.isResponse) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp)
                            ) {
                                Text(
                                    text = "►",
                                    color = highlightColor,
                                    fontSize = (uiState.fontSizeSp - 2).sp,
                                    modifier = Modifier.padding(top = 6.dp, end = 8.dp)
                                )
                                Text(
                                    text = verse.textMl,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontFamily = NotoSerifMalayalam,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = uiState.fontSizeSp.sp,
                                        lineHeight = (uiState.fontSizeSp * uiState.lineHeightMultiplier).sp,
                                        color = highlightColor,
                                    ),
                                    textAlign = TextAlign.Justify,
                                )
                            }
                        } else {
                            when {
                                verse.type == "speaker_label" -> {
                                    Text(
                                        text = verse.textMl,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontFamily = NotoSerifMalayalam,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = (uiState.fontSizeSp - 2).sp,
                                            letterSpacing = 0.5.sp,
                                        ),
                                        color = highlightColor,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp, bottom = 4.dp)
                                    )
                                }
                                isChantContext[index] -> {
                                    Text(
                                        text = verse.textMl,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontFamily = NotoSerifMalayalam,
                                            fontSize = uiState.fontSizeSp.sp,
                                            lineHeight = (uiState.fontSizeSp * uiState.lineHeightMultiplier).sp,
                                            color = readerText,
                                            fontWeight = FontWeight.Medium,
                                            fontStyle = FontStyle.Italic
                                        ),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 24.dp, vertical = 2.dp)
                                    )
                                }
                                verse.type == "ui_element" && verse.speaker == "sub_heading" -> {
                                    Text(
                                        text = verse.textMl,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontFamily = NotoSerifMalayalam,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = (uiState.fontSizeSp + 1).sp,
                                        ),
                                        textAlign = TextAlign.Center,
                                        color = Maroon,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp, bottom = 4.dp)
                                    )
                                }
                                verse.type == "ui_element" || verse.type == "instruction" || verse.speaker == "rubric" -> {
                                    Text(
                                        text = verse.textMl,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontFamily = NotoSerifMalayalam,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = (uiState.fontSizeSp - 2).sp,
                                        ),
                                        textAlign = TextAlign.Center,
                                        color = readerFaint,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                verse.speaker == "psalm" -> {
                                    Text(
                                        text = verse.textMl,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontFamily = NotoSerifMalayalam,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = (uiState.fontSizeSp - 1).sp,
                                        ),
                                        textAlign = TextAlign.Center,
                                        color = readerText,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                else -> {
                                    Text(
                                        text = verse.textMl,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontFamily = NotoSerifMalayalam,
                                            fontSize = uiState.fontSizeSp.sp,
                                            lineHeight = (uiState.fontSizeSp * uiState.lineHeightMultiplier).sp,
                                            color = readerText,
                                        ),
                                        textAlign = TextAlign.Justify,
                                    )
                                }
                            }
                        }
                    }
                }

                // "Next Section" at the bottom
                if (uiState.currentSectionIndex < uiState.totalSections - 1) {
                    val nextSection = book.sections.getOrNull(uiState.currentSectionIndex + 1)
                    if (nextSection != null) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = KoloThemeExtras.colors.border)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Next: ${nextSection.titleEn} →",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.navigateToNextSection() }
                                    .padding(vertical = 12.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelLarge,
                                color = Maroon,
                            )
                        }
                    }
                }
            }
        }

        // ── Bookmark Toast ──
        BookmarkToast(
            visible = uiState.showBookmarkToast,
            sectionName = section.titleEn,
            verseNum = uiState.highlightedVerseNum ?: 0,
            onUndo = { viewModel.undoBookmark() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
        )

        // ── Side Drawer ──
        if (uiState.showDrawer) {
            SideDrawer(
                book = book,
                currentSectionId = section.id,
                completedSections = emptySet(),
                fontSizeSp = uiState.fontSizeSp,
                currentTheme = uiState.theme,
                onClose = { viewModel.closeDrawer() },
                onSectionClick = { viewModel.navigateToSection(it) },
                onThemeChange = { viewModel.updateTheme(it) },
                onFontSizeChange = { viewModel.updateFontSize(it) },
            )
        }
    }
}
