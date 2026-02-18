document.addEventListener("DOMContentLoaded", () => {
  const { pyqPapers, pyqQuestions } = window.mockData;

  site.renderList("#pyq-list", pyqPapers, (item) => {
    const card = document.createElement("div");
    card.className = "section-card";

    const title = site.makeText("p", "card-title", item.title);
    title.style.color = "var(--indigo-700)";
    const detail = site.makeText("p", "muted", item.detail);
    detail.style.marginTop = "6px";

    const row = document.createElement("div");
    row.className = "cta-row";
    row.style.marginTop = "12px";

    const open = site.buildLink(
      `pyq.html?paper=${encodeURIComponent(item.title)}#pyq-samples`,
      "btn",
      "Open set"
    );
    const explain = site.buildLink("#pyq-samples", "btn btn-outline", "View explanations");
    row.append(open, explain);

    card.append(title, detail, row);
    return card;
  });

  site.renderList("#pyq-questions", pyqQuestions, (q) => {
    const card = document.createElement("div");
    card.className = "section-card";
    card.style.boxShadow = "none";
    card.style.borderRadius = "16px";

    const label = site.makeText("p", "pill-small", q.title);
    const prompt = site.makeText("p", "card-title", q.prompt);
    const answer = site.makeText("p", "muted", q.answer);

    card.append(label, prompt, answer);
    return card;
  });
});
