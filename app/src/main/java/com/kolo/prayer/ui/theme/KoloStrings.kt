package com.kolo.prayer.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * All user-facing UI strings. Prayer content is always Malayalam (from JSON),
 * but labels, buttons, headers switch based on the user's language choice.
 */
data class KoloStrings(
    // ── Onboarding ──
    val appTitle: String,
    val appSubtitle: String,
    val selectLanguage: String,
    val continueButton: String,
    val enterAppButton: String,
    val step2Title: String,
    val step2Subtitle: String,
    val fontSize: String,
    val lineSpacing: String,
    val theme: String,
    val welcomeTitle: String,
    val welcomeBody: String,
    val yourSettings: String,
    val scriptLabel: String,
    val fontSizeLabel: String,
    val themeLabel: String,

    // ── Home ──
    val continueReading: String,
    val seeAll: String,
    val allPrayerBooks: String,
    val listView: String,
    val gridView: String,
    val searchPlaceholder: String,
    val moreComing: String,

    // ── Book Detail ──
    val continueFrom: String,
    val sections: String,
    val complete: String,

    // ── Reader ──
    val nextSection: String,

    // ── Settings ──
    val settings: String,
    val reading: String,
    val appearance: String,
    val language: String,
    val general: String,
    val keepScreenOn: String,
    val about: String,
    val shareApp: String,
    val rateApp: String,
    val version: String,
    val appDescription: String,

    // ── Nav ──
    val books: String,

    // ── Drawer ──
    val tableOfContents: String,
    val readingSettings: String,
    val textSize: String,
    val bookmarksNotes: String,
    val aboutThisPrayer: String,

    // ── Common ──
    val daily: String,
    val liturgy: String,
    val penitential: String,
    val sacramental: String,
    val specialRite: String,
)

fun malayalamStrings() = KoloStrings(
    appTitle = "ദൈവ ഭക്തി പ്രാർത്ഥന",
    appSubtitle = "JACOBITE SYRIAN PRAYER",
    selectLanguage = "ഭാഷ തിരഞ്ഞെടുക്കുക",
    continueButton = "തുടരുക →",
    enterAppButton = "ആപ്പിലേക്ക് പ്രവേശിക്കുക ✦",
    step2Title = "വായനാ സൗകര്യം",
    step2Subtitle = "നിങ്ങൾക്ക് ഇഷ്ടമുള്ളത് തിരഞ്ഞെടുക്കുക",
    fontSize = "ഫോണ്ട് സൈസ്",
    lineSpacing = "വരി അകലം",
    theme = "തീം",
    welcomeTitle = "സ്വാഗതം",
    welcomeBody = "എല്ലാം തയ്യാർ. നിങ്ങളുടെ ക്രമീകരണങ്ങൾ\nസേവ് ചെയ്തിരിക്കുന്നു.",
    yourSettings = "നിങ്ങളുടെ ക്രമീകരണങ്ങൾ",
    scriptLabel = "എഴുത്ത്",
    fontSizeLabel = "ഫോണ്ട് സൈസ്",
    themeLabel = "തീം",

    continueReading = "വായന തുടരുക",
    seeAll = "എല്ലാം കാണുക",
    allPrayerBooks = "എല്ലാ പ്രാർത്ഥനാ പുസ്തകങ്ങളും",
    listView = "ലിസ്‌റ്റ്",
    gridView = "ഗ്രിഡ്",
    searchPlaceholder = "പ്രാർത്ഥനകൾ തിരയുക...",
    moreComing = "കൂടുതൽ വരുന്നു",

    continueFrom = "ഇവിടെ നിന്ന് തുടരുക",
    sections = "ഭാഗങ്ങൾ",
    complete = "പൂർത്തിയായി",

    nextSection = "അടുത്ത ഭാഗം",

    settings = "ക്രമീകരണങ്ങൾ",
    reading = "വായന",
    appearance = "രൂപം",
    language = "ഭാഷ",
    general = "പൊതുവായത്",
    keepScreenOn = "സ്ക്രീൻ ഓണായി നിലനിർത്തുക",
    about = "വിവരങ്ങൾ",
    shareApp = "ആപ്പ് പങ്കിടുക",
    rateApp = "ആപ്പ് റേറ്റ് ചെയ്യുക",
    version = "പതിപ്പ്",
    appDescription = "യാക്കോബായ സുറിയാനി പ്രാർത്ഥനാ ആപ്പ്",

    books = "പുസ്തകങ്ങൾ",

    tableOfContents = "ഉള്ളടക്കം",
    readingSettings = "വായനാ ക്രമീകരണങ്ങൾ",
    textSize = "ടെക്സ്‌റ്റ് സൈസ്",
    bookmarksNotes = "ബുക്ക്മാർക്കുകൾ",
    aboutThisPrayer = "ഈ പ്രാർത്ഥനയെ കുറിച്ച്",

    daily = "ദൈനംദിനം",
    liturgy = "ലിറ്റർജി",
    penitential = "പ്രായശ്ചിത്തം",
    sacramental = "കൂദാശ",
    specialRite = "പ്രത്യേക ശുശ്രൂഷ",
)

fun englishStrings() = KoloStrings(
    appTitle = "Kolo Prayer",
    appSubtitle = "JACOBITE SYRIAN PRAYER",
    selectLanguage = "Select Language",
    continueButton = "Continue →",
    enterAppButton = "Enter the App ✦",
    step2Title = "Reading Comfort",
    step2Subtitle = "Adjust to what feels right for you",
    fontSize = "Font Size",
    lineSpacing = "Line Spacing",
    theme = "Theme",
    welcomeTitle = "Welcome",
    welcomeBody = "You're all set. Your preferences are saved\nand can be changed anytime in settings.",
    yourSettings = "Your Settings",
    scriptLabel = "Script",
    fontSizeLabel = "Font size",
    themeLabel = "Theme",

    continueReading = "Continue Reading",
    seeAll = "See all",
    allPrayerBooks = "All Prayer Books",
    listView = "List",
    gridView = "Grid",
    searchPlaceholder = "Search prayers, books...",
    moreComing = "More coming",

    continueFrom = "Continue from",
    sections = "sections",
    complete = "complete",

    nextSection = "Next Section",

    settings = "Settings",
    reading = "Reading",
    appearance = "Appearance",
    language = "Language",
    general = "General",
    keepScreenOn = "Keep Screen On",
    about = "About",
    shareApp = "Share App",
    rateApp = "Rate App",
    version = "Version",
    appDescription = "Jacobite Syrian Prayer App",

    books = "Books",

    tableOfContents = "Table of Contents",
    readingSettings = "Reading Settings",
    textSize = "Text size",
    bookmarksNotes = "Bookmarks & Notes",
    aboutThisPrayer = "About this Prayer",

    daily = "Daily",
    liturgy = "Liturgy",
    penitential = "Penitential",
    sacramental = "Sacramental",
    specialRite = "Special Rite",
)

val LocalKoloStrings = staticCompositionLocalOf { englishStrings() }

object Strings {
    val current: KoloStrings
        @Composable get() = LocalKoloStrings.current
}
