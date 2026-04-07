package com.kolo.prayer.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolo.prayer.ui.components.*
import com.kolo.prayer.ui.theme.*

import androidx.compose.material3.pulltorefresh.PullToRefreshBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onBookClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val strings = Strings.current
    var selectedNavIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedIndex = selectedNavIndex,
                onTabSelected = { index ->
                    selectedNavIndex = index
                    if (index == 1) onSettingsClick()
                },
                items = listOf(
                    NavBarItem(strings.books, Icons.Default.Menu),
                    NavBarItem(strings.settings, Icons.Default.Settings),
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
            // ── Maroon Header ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Maroon)
                    .statusBarsPadding()
                    .padding(horizontal = 18.dp, vertical = 14.dp)
            ) {
                val greetingText = if (uiState.language == "ml") {
                    uiState.greeting
                } else {
                    uiState.greetingEn
                }
                Text(
                    text = greetingText,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.65f),
                    letterSpacing = 0.5.sp,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (uiState.language == "ml") "ദൈവ ഭക്തി പ്രാർത്ഥന" else "Kolo Prayer",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = NotoSerifMalayalam,
                        color = Color.White,
                    ),
                )
                Text(
                    text = "Jacobite Syrian Prayer Book",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f),
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Search bar
                val focusRequester = remember { FocusRequester() }

                if (uiState.isSearchActive) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .border(0.5.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        BasicTextField(
                            value = uiState.searchQuery,
                            onValueChange = { viewModel.onSearchQueryChange(it) },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester),
                            textStyle = MaterialTheme.typography.labelMedium.copy(
                                color = Color.White,
                            ),
                            cursorBrush = SolidColor(Color.White),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                if (uiState.searchQuery.isEmpty()) {
                                    Text(
                                        text = strings.searchPlaceholder,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.White.copy(alpha = 0.5f),
                                    )
                                }
                                innerTextField()
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { viewModel.toggleSearch() },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.size(14.dp),
                            )
                        }
                    }

                    LaunchedEffect(Unit) { focusRequester.requestFocus() }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .border(0.5.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .clickable { viewModel.toggleSearch() }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = strings.searchPlaceholder,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.5f),
                        )
                    }
                }
            }

            // ── Continue Reading ──
            if (uiState.recentBooks.isNotEmpty() && !uiState.isSearchActive) {
                SectionHeader(
                    title = strings.continueReading,
                    action = strings.seeAll,
                    onAction = { /* show all books view */ },
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(uiState.recentBooks) { recent ->
                        RecentCard(
                            titleMl = recent.titleMl,
                            subtitleEn = recent.subtitleEn,
                            symbol = recent.symbol,
                            colorHex = recent.colorHex,
                            progress = recent.progress,
                            onClick = { onBookClick(recent.bookId) },
                        )
                    }
                }
            }

            // ── All Prayer Books ──
            SectionHeader(
                title = strings.allPrayerBooks,
                action = if (uiState.isListView) strings.gridView else strings.listView,
                onAction = { viewModel.toggleViewMode() },
            )

            val displayBooks = uiState.filteredBooks

            if (uiState.isListView) {
                // List view
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    displayBooks.forEach { book ->
                        ListBookItem(
                            book = book,
                            onClick = { onBookClick(book.id) },
                            language = uiState.language,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            } else {
                // Grid view (2 columns)
                val bookRows = (displayBooks.size + 1) / 2
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    for (rowIndex in 0 until bookRows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            val leftIndex = rowIndex * 2
                            val rightIndex = rowIndex * 2 + 1

                            if (leftIndex < displayBooks.size) {
                                BookCard(
                                    titleMl = displayBooks[leftIndex].titleMl,
                                    titleEn = displayBooks[leftIndex].titleEn,
                                    sectionCount = displayBooks[leftIndex].sectionCount,
                                    symbol = displayBooks[leftIndex].symbol,
                                    colorHex = displayBooks[leftIndex].colorHex,
                                    category = displayBooks[leftIndex].category,
                                    isDaily = displayBooks[leftIndex].isDaily,
                                    onClick = { onBookClick(displayBooks[leftIndex].id) },
                                    modifier = Modifier.weight(1f),
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }

                            if (rightIndex < displayBooks.size) {
                                BookCard(
                                    titleMl = displayBooks[rightIndex].titleMl,
                                    titleEn = displayBooks[rightIndex].titleEn,
                                    sectionCount = displayBooks[rightIndex].sectionCount,
                                    symbol = displayBooks[rightIndex].symbol,
                                    colorHex = displayBooks[rightIndex].colorHex,
                                    category = displayBooks[rightIndex].category,
                                    isDaily = displayBooks[rightIndex].isDaily,
                                    onClick = { onBookClick(displayBooks[rightIndex].id) },
                                    modifier = Modifier.weight(1f),
                                )
                            } else {
                                // "More coming" placeholder
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(130.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(1.dp, KoloThemeExtras.colors.border, RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("+", fontSize = 20.sp, color = KoloThemeExtras.colors.inkFaint)
                                        Text(
                                            strings.moreComing,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = KoloThemeExtras.colors.inkFaint,
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        } // Column
        } // PullToRefreshBox
    } // Scaffold
} // HomeScreen

@Composable
private fun ListBookItem(
    book: com.kolo.prayer.data.model.BookSummary,
    onClick: () -> Unit,
    language: String,
) {
    val coverColor = try {
        Color(android.graphics.Color.parseColor(book.colorHex))
    } catch (_: Exception) { Maroon }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(coverColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(book.symbol, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = book.titleMl,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = NotoSerifMalayalam,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                ),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "${book.titleEn} · ${book.sectionCount} ${Strings.current.sections}",
                style = MaterialTheme.typography.labelSmall,
                color = KoloThemeExtras.colors.inkFaint,
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    action: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onBackground,
        )
        if (action != null) {
            Text(
                text = action,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .then(if (onAction != null) Modifier.clickable { onAction() } else Modifier)
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall,
                color = Maroon,
            )
        }
    }
}
