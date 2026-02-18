document.addEventListener("DOMContentLoaded", () => {
  const { testsList, currentAffairsArticles } = window.mockData;
  const checklistItems = [
    "Daily MCQ drills",
    "Monthly CA tests",
    "PYQ explorer",
    "Flashcards & notes",
  ];

  const planItems = [
    { title: "History MCQs", badge: "20 Qs", tone: "solid" },
    { title: "Current Affairs", badge: "12 min", tone: "soft" },
    { title: "PYQ set", badge: "Paper 2023", tone: "soft" },
  ];

  const featureCards = [
    {
      title: "Subject dashboards",
      text: "Jump into History, Polity, Geography, Economy, and more with unit-wise drills.",
    },
    {
      title: "Tests & results",
      text: "Timed sessions, bookmarks, and instant explanations to focus your revision.",
    },
    {
      title: "Notes & flashcards",
      text: "Organize notes, tag subjects, and swipe through flashcards to revise quickly.",
    },
  ];

  const checklist = site.qs("#hero-checklist");
  if (checklist) {
    checklist.innerHTML = "";
    checklistItems.forEach((item) => {
      const pill = document.createElement("div");
      pill.className = "pill";
      pill.textContent = item;
      checklist.appendChild(pill);
    });
  }

  const planList = site.qs("#plan-list");
  if (planList) {
    planList.innerHTML = "";
    planItems.forEach((item) => {
      const row = document.createElement("div");
      row.className = "section-card";
      row.style.padding = "12px 14px";
      row.style.boxShadow = "none";
      row.style.borderRadius = "16px";
      row.style.background = "rgba(255,255,255,0.06)";
      row.style.border = "1px solid rgba(255,255,255,0.08)";

      const top = document.createElement("div");
      top.style.display = "flex";
      top.style.alignItems = "center";
      top.style.justifyContent = "space-between";
      top.style.gap = "8px";

      const title = document.createElement("span");
      title.textContent = item.title;
      title.style.fontWeight = "700";

      const badge = document.createElement("span");
      badge.textContent = item.badge;
      badge.style.fontSize = "12px";
      badge.style.fontWeight = "800";
      badge.style.padding = "6px 10px";
      badge.style.borderRadius = "999px";
      badge.style.background =
        item.tone === "solid" ? "rgba(99,102,241,0.9)" : "rgba(255,255,255,0.08)";
      badge.style.color = "#fff";
      badge.style.border =
        item.tone === "solid" ? "none" : "1px solid rgba(255,255,255,0.14)";

      top.appendChild(title);
      top.appendChild(badge);
      row.appendChild(top);
      planList.appendChild(row);
    });
  }

  const featureGrid = site.qs("#feature-grid");
  if (featureGrid) {
    featureGrid.innerHTML = "";
    featureCards.forEach((card) => {
      const item = document.createElement("div");
      item.className = "section-card";

      const title = document.createElement("p");
      title.className = "card-title";
      title.style.color = "var(--indigo-700)";
      title.textContent = card.title;

      const text = document.createElement("p");
      text.className = "muted";
      text.textContent = card.text;
      text.style.marginTop = "8px";

      item.appendChild(title);
      item.appendChild(text);
      featureGrid.appendChild(item);
    });
  }

  const previewGrid = site.qs("#tests-preview-grid");
  if (previewGrid) {
    previewGrid.innerHTML = "";
    testsList.slice(0, 4).forEach((test) => {
      const card = document.createElement("div");
      card.className = "section-card";

      const subject = document.createElement("p");
      subject.className = "card-title";
      subject.style.color = "var(--indigo-700)";
      subject.textContent = test.subject;

      const name = document.createElement("p");
      name.className = "card-title";
      name.textContent = test.title;

      const meta = document.createElement("p");
      meta.className = "muted";
      meta.textContent = site.formatTestMeta(test);

      const cta = site.buildLink(
        `test.html?id=${encodeURIComponent(test.id)}`,
        "btn",
        "Start"
      );
      cta.style.marginTop = "12px";

      card.appendChild(subject);
      card.appendChild(name);
      card.appendChild(meta);
      card.appendChild(cta);
      previewGrid.appendChild(card);
    });
  }

  const articlesGrid = site.qs("#articles-preview");
  if (articlesGrid) {
    articlesGrid.innerHTML = "";
    currentAffairsArticles.forEach((article) => {
      const card = document.createElement("div");
      card.className = "section-card";

      const category = document.createElement("p");
      category.className = "card-title";
      category.style.color = "var(--indigo-700)";
      category.textContent = article.category;

      const title = document.createElement("p");
      title.className = "card-title";
      title.textContent = article.title;

      const meta = document.createElement("p");
      meta.className = "muted";
      meta.textContent = `${article.date} · ${article.readTime}`;

      card.appendChild(category);
      card.appendChild(title);
      card.appendChild(meta);
      articlesGrid.appendChild(card);
    });
  }
});
