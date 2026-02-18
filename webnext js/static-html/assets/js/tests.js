document.addEventListener("DOMContentLoaded", () => {
  const { testSubjects, testsList } = window.mockData;

  site.renderList("#test-grid", testSubjects, (subject) => {
    const card = document.createElement("div");
    card.className = "section-card";

    const title = site.makeText("p", "card-title", subject.name);
    title.style.color = "var(--indigo-700)";
    const detail = site.makeText("p", "muted", subject.detail);

    const firstMatch = testsList.find(
      (t) => t.subject.toLowerCase() === subject.name.toLowerCase()
    );
    const startHref = firstMatch
      ? `test.html?id=${encodeURIComponent(firstMatch.id)}`
      : "#test-list";

    const row = document.createElement("div");
    row.className = "cta-row";
    row.style.marginTop = "12px";

    const startBtn = site.buildLink(startHref, "btn", "Start test");
    const unitBtn = site.buildLink("#test-list", "btn btn-outline", "Units");

    row.append(startBtn, unitBtn);
    card.append(title, detail, row);
    return card;
  });

  site.renderList("#test-list-items", testsList, (test) => {
    const card = document.createElement("div");
    card.className = "section-card";
    card.style.boxShadow = "none";
    card.style.borderRadius = "16px";

    const subject = site.makeText("p", "card-title", test.subject);
    subject.style.color = "var(--indigo-700)";
    const name = site.makeText("p", "card-title", test.title);
    const meta = site.makeText("p", "muted", site.formatTestMeta(test));
    const desc = test.description
      ? site.makeText("p", "subtle", test.description)
      : null;

    const cta = site.buildLink(
      `test.html?id=${encodeURIComponent(test.id)}`,
      "btn",
      "Start"
    );
    cta.style.marginTop = "10px";

    card.append(subject, name, meta);
    if (desc) card.append(desc);
    card.append(cta);
    return card;
  });
});
