export const dashboardHighlights = [
  { title: "Streak", value: "7 days", detail: "Stay consistent" },
  { title: "Today’s tasks", value: "3/5", detail: "MCQs, CA, PYQ" },
  { title: "Next test", value: "History", detail: "10 mins" },
];

export const subjects = [
  "History",
  "Polity",
  "Geography",
  "Economy",
  "Environment",
  "Science & Tech",
];

export const testSubjects = [
  { name: "History", detail: "Full length + unit tests" },
  { name: "Polity", detail: "Lakshmikant units" },
  { name: "Geography", detail: "Physical & Human" },
  { name: "Economy", detail: "Macro & schemes" },
  { name: "Environment", detail: "Ecology & Acts" },
  { name: "Science & Tech", detail: "Space, bio, cyber" },
];

export const testsList = [
  {
    id: "hist-full",
    title: "History Full Length",
    subject: "History",
    duration: "10 min",
    questions: 10,
    description: "Modern India + Medieval mix, balanced difficulty.",
  },
  {
    id: "polity-laksh",
    title: "Polity — Lakshmikant Unit 2",
    subject: "Polity",
    duration: "10 min",
    questions: 10,
    description: "Fundamental Rights and DPSP quick drill.",
  },
  {
    id: "geo-physical",
    title: "Geography — Physical",
    subject: "Geography",
    duration: "10 min",
    questions: 10,
    description: "Geomorphology and climatology snapshots.",
  },
  {
    id: "eco-schemes",
    title: "Economy — Schemes",
    subject: "Economy",
    duration: "10 min",
    questions: 10,
    description: "Flagship schemes, ministries, and benefits.",
  },
];

export const testQuestions: Record<
  string,
  { prompt: string; options: string[]; answer: string }[]
> = {
  "hist-full": [
    {
      prompt: "Which session of Congress adopted Poorna Swaraj?",
      options: ["Lahore, 1929", "Karachi, 1931", "Lucknow, 1916", "Madras, 1927"],
      answer: "Lahore, 1929",
    },
    {
      prompt: "Who led the Wahabi movement in India?",
      options: ["Syed Ahmed Barelvi", "Aga Khan", "Sir Syed", "Nana Saheb"],
      answer: "Syed Ahmed Barelvi",
    },
  ],
  "polity-laksh": [
    {
      prompt: "Which Article covers the Right to Constitutional Remedies?",
      options: ["Art. 32", "Art. 19", "Art. 14", "Art. 21"],
      answer: "Art. 32",
    },
    {
      prompt: "Directive Principles are",
      options: ["Non-justiciable", "Justiciable", "Fundamental rights", "Customary laws"],
      answer: "Non-justiciable",
    },
  ],
  "geo-physical": [
    {
      prompt: "El Niño is associated with",
      options: ["Warming of Pacific", "Cooling of Atlantic", "Monsoon onset", "Ozone depletion"],
      answer: "Warming of Pacific",
    },
    {
      prompt: "The ITCZ shifts north in",
      options: ["June–July", "December–January", "April", "October"],
      answer: "June–July",
    },
  ],
  "eco-schemes": [
    {
      prompt: "PM-KISAN provides income support to",
      options: ["Farmer families", "MSMEs", "SHGs", "Startups"],
      answer: "Farmer families",
    },
    {
      prompt: "Skill India Mission is implemented by",
      options: ["MSDE", "MoE", "MoSPI", "NITI"],
      answer: "MSDE",
    },
  ],
};

export const pyqPapers = [
  { title: "GS Paper I (2023)", detail: "History, Geography, Society" },
  { title: "GS Paper II (2023)", detail: "Polity, Governance, IR" },
  { title: "GS Paper III (2023)", detail: "Economy, S&T, Environment" },
  { title: "GS Paper IV (2023)", detail: "Ethics, Integrity, Aptitude" },
];

export const pyqQuestions = [
  {
    title: "GS Paper I (2023)",
    prompt: "Discuss the role of women in freedom movements across regions.",
    answer: "Cover regional movements, leadership roles, and impact on mass mobilization.",
  },
  {
    title: "GS Paper II (2023)",
    prompt: "Critically assess cooperative federalism in recent policy contexts.",
    answer: "Explain GST Council, finance commissions, and centre-state schemes.",
  },
];

export const notesCards = [
  { title: "Notes hub", detail: "Recent notes, subject folders, tags" },
  { title: "Flashcards", detail: "Swipe/rotate with subject colors" },
  { title: "Reference", detail: "Books → units → note lists" },
];

export const noteFolders = [
  { name: "History", count: 12 },
  { name: "Polity", count: 9 },
  { name: "Geography", count: 7 },
  { name: "Economy", count: 8 },
  { name: "Environment", count: 5 },
  { name: "Science & Tech", count: 6 },
];

export const recentNotes = [
  {
    id: "note-1",
    title: "Civil Disobedience nuances",
    subject: "History",
    tags: ["freedom", "movements"],
    updated: "2d ago",
  },
  {
    id: "note-2",
    title: "Fundamental Duties quick list",
    subject: "Polity",
    tags: ["constitution"],
    updated: "1d ago",
  },
  {
    id: "note-3",
    title: "El Niño vs La Niña",
    subject: "Geography",
    tags: ["climate"],
    updated: "4h ago",
  },
];

export const currentAffairsItems = [
  { title: "Weekly digest", detail: "National, Economy, S&T, Environment" },
  { title: "Monthly CA test", detail: "Timer + explanations" },
  { title: "Bookmarks", detail: "Save important articles" },
];

export const currentAffairsArticles = [
  {
    title: "New MSP revision and fiscal impact",
    category: "Economy",
    date: "Today",
    readTime: "6 min",
  },
  {
    title: "Global climate summit outcomes",
    category: "Environment",
    date: "Yesterday",
    readTime: "5 min",
  },
  {
    title: "India’s semiconductor push",
    category: "Science & Tech",
    date: "2d ago",
    readTime: "7 min",
  },
];
