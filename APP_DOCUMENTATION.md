# Dream IAS Elite — Updated Product & Flow Overview

## Core Concept
- UPSC companion covering dashboards, subject drills, monthly/current affairs, PYQs, notes/flashcards, study planner, and profile.
- Android, Jetpack Compose + Material 3; single `NavHost` in `MainActivity.kt` handles all routes. Splash → Auth gate → main shell.
- Theme controlled via `LocalThemeController`; light/dark toggle lives in Theme & Appearance.

## Navigation & Shell
- Bottom bar tabs: `dashboard`, `tests`, `pyq`, `notes` (Current Affairs is accessible via routes but hidden from the bar).
- Drawer sections: Account (Profile, Progress placeholder), Study Tools (Study Planner, Downloads placeholder), App (Theme & Appearance, Help, About & Privacy, Logout).
- Top app bar title reflects route; hamburger opens drawer. Back stack preserves state for tab roots.
- Key routes (URI-decoded):
  - Home: `dashboard`, `subject_dashboard/{subject}`
  - Current Affairs: `current_affairs`, `current_affair_detail/{articleId}`, `monthly_ca_test/{testId}`, `monthly_ca_test_session/{testId}`
  - Tests: `tests`, `test_books/{subject}`, `test_units/{subject}/{book}`, `test_subject/{name}`, `test_session/{subject}`, `test_result`
  - PYQ: `pyq`, `pyq_paper/{paperId}`, `pyq_test/{paperId}`, `pyq_test_result/{paperId}`
  - Notes: `notes`, `notes_last_opened/{subject}`, `notes_flashcards/{subject}`, `notes_reference/{subject}`, `notes_reference_units/{subject}/{book}`, `notes_unit_notes/{subject}/{book}/{unit}`
  - Profile/Utilities: `profile`, `study_planner`, `theme_appearance`, `help_feedback`, `about_privacy`

## Auth & Profile
- `AuthScreen` handles login/register with validation; guarded entry until authenticated.
- `AuthViewModel` + `AuthRepository` + Room `UserDao` + DataStore for session/email/avatar.
- Profile screen: view/edit name/target year, change photo via cropper (with runtime permission), avatar persisted to internal storage and prefs. Snackbar feedback on save.

## Dashboard & Subject Dashboards
- Home shows hero card (days to exam, daily progress), subject chips, Monthly Current Affairs and Monthly CA Test carousels.
- Subject dashboard (`subject_dashboard/{subject}`): daily MCQ starters (open test session), CA articles (detail), and quick note links (last opened, flashcards). Accuracy card placeholder.

## Tests & Monthly CA Tests
- Tests hub lists subjects; each subject opens reference books, then units. Full-length subject test and per-unit tests launch `test_session/{subject}`.
- Question pools are subject-specific; a long scenario MCQ is injected as the first question. Max 10 questions/session.
- **Full-Screen Layout:** Test session and results use a clean, full-screen layout without block-style cards for questions or answers.
- **MCQ Deselection:** Users can deselect an option by clicking the currently selected answer again.
- Test session: 10-minute timer, bookmark toggle per question, previous/next, submit, auto-submit on timeout. Answers/bookmarks persisted in state; scrollable stem/options.
- Results screen: score, time taken, per-question selected vs correct with explanation; back-to-tests CTA.
- Monthly CA tests (`monthly_ca_test/{id}`) show overview + focus areas; “Start Test” routes to monthly CA test session (reuses test session with test title).

## Current Affairs
- `CurrentAffairsScreen`: search + chip filters (National/International/Polity/Economy/etc.), list cards with category/importance badges, bookmark toggle, “Read Full Article” to detail.
- Detail screen shows badges, meta, summary, static guidance, and back CTA.
- Current affairs items also surface on Dashboard and Subject Dashboard cards.

## PYQ
- Tabs: Year-wise vs Subject-wise selectors. Cards show paper title, question count, status/score badges, and open detail.
- **Full-Screen Detail/Test:** PYQ paper details, tests, and result reviews use a full-screen layout with dividers for a clean reading experience.
- Detail screen lists questions with answer + explanation. (Export/download not yet implemented.)
- Attempt summary card, stats tiles, and rotating study tip with swipe/auto-advance.

## Notes & Reference
- Notes hub: folder grid (counts by subject), total notes card, recent notes list with search/filter by query/tags and optional subject filter. “View All” reveals full list.
- Notes carry subject tag, date, preview, tags, share intent action. Note-taking tip card auto-rotates (20s) with manual swipe.
- Flashcards available when navigated via subject (`notes_flashcards/{subject}`); last opened subject path supported.
- Reference flows: subject → reference books → units → unit notes (`notes_reference/*`, `notes_unit_notes/*`) with demo content.

## Study Planner
- Add/edit checklist for today’s tasks; quick add dialog, edit picker, edit dialog. Tracks done state; summary card (study time/tasks/streak). (Weekly targets removed for simplicity.)

## Theme, Help, About
- Theme & Appearance: Light/Dark toggle. (Accent color selection removed.)
- Help: Frequently Asked Questions (FAQ) section with quick help tips for common issues.
- About & Privacy: app blurb, version placeholder, privacy highlights, full policy coming soon.

## Data & Persistence
- Auth/profile persisted via Room + DataStore; avatar cached to internal files and keyed per email.
- Tests, CA articles, PYQs, notes, planner tasks use in-memory/static data; no backend wiring yet.
- Splash uses Android 12+ SplashScreen API; navigation state remembered for tabs/drawer.

## Defaults & Behaviours
- Test timer: 10 minutes; first question is the scenario MCQ. Max 10 questions per session.
- Tip/flashcard rotation interval: 20 seconds; manual swipe threshold ~60px horizontal drag.
- Bottom bar highlights root section (tests/pyq/notes/dashboard) even when on nested routes; Current Affairs intentionally hidden from bottom bar but reachable.

## Next Steps (suggested)
- Replace mock data with backend sources for tests, CA, PYQ, notes, planner, and sync bookmarks/answers.
- Add PDF/export/download for PYQ and CA; persist notes and planner tasks; expose Current Affairs as a tab if required.

## Screen-by-Screen Breakdown (UI pieces & behaviour)

**Shell (Splash/Auth/App)**
- `SplashActivity` shows Android 12+ splash then enters `MainActivity`.
- `MainActivity` wraps theme, provides `ThemeController`, shows `AuthScreen` until authenticated (`AuthViewModel.uiState`).
- Shell scaffold: top bar (title per route + drawer icon), modal drawer (Account/Study Tools/App sections), bottom bar (Dashboard/Tests/PYQ/Notes). Drawer “Logout” opens confirm dialog then clears session and pops to home.

**Dashboard (`dashboard`)**
- Hero card: countdown text, daily progress meter (0/3, linear bar), greeting using `authState.currentUserName`.
- Subject chip row opens `subject_dashboard/{subject}`.
- Monthly Current Affairs and Monthly CA Test horizontal carousels: cards show tag/meta/title/subtitle; tap opens detail (`current_affair_detail/{id}`) or monthly CA test (`monthly_ca_test/{id}`); “View all” reveals more cards (load-more state).

**Subject Dashboard (`subject_dashboard/{subject}`)**
- Back icon, title/subtitle. Accuracy card placeholder.
- Sections:
  - Daily MCQs (2 cards) → `test_session/{subject - title}`.
  - CA articles list → `current_affair_detail/{id}` when available.
  - Notes quick links: “Last opened note” → `notes_last_opened/{subject}`, “Flashcards” → `notes_flashcards/{subject}`.

**Tests Hub (`tests`)**
- Subject grid (History/Geography/Polity/Economy/Environment/Science). Tap → `test_books/{subject}`.
- Highlight card explains flow.

**Subject Books (`test_books/{subject}`)**
- Back icon + subject header. Full-length test CTA starts `test_session/{subject Full Length}`.
- List of reference books (title/author/summary, tests available). Tap “Open units” → `test_units/{subject}/{book}`.
- Data source: `buildSubjectBooks()` local list per subject.

**Book Units (`test_units/{subject}/{book}`)**
- Back icon + book header. Each unit card shows name/description/tests available; “Start Unit Test” → `test_session/{book - unit}`.
- Data source: units defined inside `buildSubjectBooks()`.

**Test Subject Quick Screen (`test_subject/{name}`)**
- Full-screen information section for "Quick Subject Test" → `test_session/{subject}`.

**Test Session (`test_session/{subject}` & monthly_ca_test_session)**
- 10-question list built via `generateQuestionsFor(subject)`:
  - First question: fixed scenario MCQ (policy/agri prompt).
  - Remaining 9 from subject-specific pools (`historyQuestions`, `geographyQuestions`, etc.). Fallback to `generalQuestions`.
- UI: timer pill (10 min), bookmark toggle per question, full-screen question/option layout with selection/deselection, Previous/Next/Submit fixed row. Auto-submit on timer = 0.
- State: `rememberSaveable` for timer, index, selections, bookmarks. On submit -> `TestResultScreen` via saved state handle with `TestResultData`.

**Test Result (`test_result`)**
- Computes score from selections. Shows subject, score, time taken, and full-screen question list (user answer vs correct, explanation). “Back to Tests” pops to `tests`.

**Monthly CA Test (`monthly_ca_test/{id}`)**
- Pulls `MonthlyCaTestData.getById`. Full-screen overview (questions, duration, difficulty, last updated) and focus areas list. “Start Test” → `monthly_ca_test_session/{id}` which uses TestSessionScreen with test title.

**Current Affairs (`current_affairs`)**
- Search bar + chip filters (All, National, International, Polity & Governance, Economy, Environment & Ecology, Science & Tech, IR, Security, Social Issues, Schemes, Reports, Places, Bills & Acts, Misc).
- Weekly highlight card. Article cards show category/importance badges, title/summary/date/read time, bookmark toggle, “Read Full Article”.
- Data: `CurrentAffairsData.articles` static list; filter uses chip + search. Bookmark stored in local state set.
- Detail (`current_affair_detail/{articleId}`): title, badges, meta, summary card, guidance text, Back button.

**PYQ (`pyq`)**
- Tab toggle (Year-wise/Subject-wise). Year selector or subject selector chips.
- Attempt summary (progress bar), stats tiles, study tip card with auto-rotate 20s and swipe gestures.
- Paper cards (title, questions, optional status/score badge). Tap → `pyq_paper/{paperId}`.
- Detail/Test/Result: Full-screen question lists with explanations. Data from `providePyqPapers()` and `providePyqQuestions()` static lists.

**Notes (`notes`)**
- Total notes card (blue); folders grid (History/Polity/Geography/Economy/Science & Tech/Environment counts).
- Recent notes list with subject tag/date/title/preview/tags + share action (Intent.ACTION_SEND). “View All” expands full filtered list.
- Optional subject filter via route (`notes_last_opened/{subject}` focuses last note, `notes_flashcards/{subject}` injects flashcards section).
- Flashcards: built by `buildFlashcardsForSubject`; swipe/auto-rotate every 20s with background color animation.
- Tip card: auto-rotate 20s, swipe threshold ~60px.
- Reference flows:
  - `notes_reference/{subject}` → reference books list (static map in `buildReferenceBooks`, demo content).
  - `notes_reference_units/{subject}/{book}` → units list with nav to `notes_unit_notes/{subject}/{book}/{unit}`.
  - `notes_unit_notes/{subject}/{book}/{unit}` → demo notes list (summary + “Note 1..n” text).
- Data: all notes/flashcards/reference content are in-memory lists; no persistence yet.

**Profile (`profile`)**
- Displays avatar (loaded from stored Uri), initials fallback, name/email/target year, joined date, target/streak chips.
- Buttons: “Edit profile” bottom sheet (name, target year validation 2024–2100), “Change photo” (permission + cropper). Snackbar for save result. Uses `AuthViewModel.updateProfile`.

**Study Planner (`study_planner`)**
- Summary card (hours, tasks, streak placeholder).
- Today’s plan card with add/edit dialogs, checkbox rows (strike-through when done).
- Data held in Compose state only; not persisted.

**Theme & Appearance (`theme_appearance`)**
- Light/Dark radio options toggle `ThemeController.isDarkMode`.

**Help (`help_feedback`)**
- Frequently Asked Questions (FAQ) with answers. Quick help text block.

**About & Privacy (`about_privacy`)**
- App description, version placeholder, privacy highlights, and “full policy coming soon” text. Static content.

## Data & Persistence (implementation details)
- **Auth**: Room (`AppDatabase`, `UserDao`, `UserEntity`) stores users; password hashed with SHA-256 + salt. DataStore stores `current_user_email` and per-email avatar Uri. `AuthRepository` mediates register/login/logout/updateProfile and persists avatar both in DB and DataStore.
- **Profile photo**: saved to internal files (`filesDir/avatars/avatar_<email>.jpg`) when updated; Uri stored in DB/DataStore. Loaded with `rememberBitmapFromUri` using IO dispatcher.
- **Session flow**: `AuthViewModel` initializes from DataStore/DB; if stored email has no DB user, clears session and shows error prompting re-login.
- **Content data**: Tests, CA articles, PYQ papers/questions, notes, flashcards, reference books/units, planner tasks are static/in-memory within composables or singleton objects (no network/database fetch). Monthly CA tests come from `MonthlyCaTestData.tests` list. Test questions generated locally by subject.

## Build & Run Notes
- Android app, Jetpack Compose. Use project Gradle wrapper (`./gradlew assembleDebug` or `./gradlew.bat assembleDebug` on Windows).
- Min/target SDK: see module `build.gradle.kts` (minSdk 24, target/compile per SDK in file). JDK 17+ recommended.
- Clean session for testing: clear app storage or delete Room DB `dream_ias_elite.db` and DataStore files (`auth_prefs`) from app data.

## Testing & QA Pointers
- Key manual checks: auth (validation, error surfaces, session clear), navigation between tabs/drawer routes, test timer auto-submit, bookmark toggle, result accuracy, share intent on notes, help FAQ, monthly CA test start, theme toggle persistence.
- Add unit tests for AuthRepository hashing/session, ViewModel state changes, and question generation per subject. Consider Compose UI tests for navigation and timer/auto-submit.

## Permissions, Logging, Analytics
- Permissions: READ_MEDIA_IMAGES or READ_EXTERNAL_STORAGE (for avatar selection); requested at runtime on profile photo change.
- Logging/analytics: none wired; no crash reporting. Add structured logging if backend integration starts.

## Accessibility & Localization
- Language: English only; no localization yet.
- Accessibility: standard Material components, but needs audit for content descriptions on icons, contrast in gradient cards, and larger font scaling tests.

## Data Sources (replace with APIs later)
- Auth/Profile: Room + DataStore (persistent).
- Tests/Monthly CA: In-memory subject/book/unit lists and question pools inside `TestsScreen` and related helpers; `MonthlyCaTestData` singleton.
- Current Affairs: static list in `CurrentAffairsData`.
- PYQ: static paper/question lists in `PyqScreen` helpers.
- Notes/Reference/Flashcards: static lists in `NotesScreen` and `buildReferenceBooks`; unit notes are demo text.
- Planner/Theme accent choices: in-memory UI state only.
