document.addEventListener("DOMContentLoaded", () => {
  const { dashboardHighlights, subjects, testsList, currentAffairsArticles } =
    window.mockData;

  site.renderList("#highlight-grid", dashboardHighlights, (item) => {
    const card = document.createElement("div");
    card.className = "section-card";

    const title = site.makeText("p", "card-title", item.title);
    title.style.color = "var(--indigo-700)";
    const value = site.makeText("p", "section-lead", item.value);
    value.style.fontSize = "24px";
    const detail = site.makeText("p", "muted", item.detail);

    card.append(title, value, detail);
    return card;
  });

  site.renderList("#subjects-grid", subjects, (subject) => {
    const chip = document.createElement("div");
    chip.className = "chip";
    chip.textContent = subject;
    return chip;
  });

  site.renderList("#next-tests", testsList.slice(0, 3), (test) => {
    const card = document.createElement("div");
    card.className = "section-card";

    const subject = site.makeText("p", "card-title", test.subject);
    subject.style.color = "var(--indigo-700)";
    const title = site.makeText("p", "card-title", test.title);
    const meta = site.makeText("p", "muted", site.formatTestMeta(test));

    const cta = site.buildLink(
      `tests.html?start=${encodeURIComponent(test.id)}#test-grid`,
      "btn",
      "Start"
    );
    cta.style.marginTop = "10px";

    card.append(subject, title, meta, cta);
    return card;
  });

  site.renderList("#ca-cards", currentAffairsArticles, (article) => {
    const card = document.createElement("div");
    card.className = "section-card";

    const category = site.makeText("p", "card-title", article.category);
    category.style.color = "var(--indigo-700)";
    const title = site.makeText("p", "card-title", article.title);
    const meta = site.makeText(
      "p",
      "muted",
      `${article.date} · ${article.readTime}`
    );

    card.append(category, title, meta);
    return card;
  });
});
