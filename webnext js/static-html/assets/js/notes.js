document.addEventListener("DOMContentLoaded", () => {
  const { notesCards, noteFolders, recentNotes } = window.mockData;

  site.renderList("#notes-cards", notesCards, (card) => {
    const node = document.createElement("div");
    node.className = "section-card";

    const title = site.makeText("p", "card-title", card.title);
    title.style.color = "var(--indigo-700)";
    const detail = site.makeText("p", "muted", card.detail);
    detail.style.marginTop = "6px";

    const link = site.buildLink("#notes-list", "btn", "View");
    link.style.marginTop = "12px";

    node.append(title, detail, link);
    return node;
  });

  site.renderList("#folder-grid", noteFolders, (folder) => {
    const card = document.createElement("div");
    card.className = "section-card";
    card.style.boxShadow = "none";
    card.style.borderRadius = "14px";

    const name = site.makeText("p", "card-title", folder.name);
    name.style.color = "var(--indigo-700)";
    const count = site.makeText("p", "muted", `${folder.count} notes`);
    count.style.marginTop = "4px";

    card.append(name, count);
    return card;
  });

  site.renderList("#recent-notes", recentNotes, (note) => {
    const card = document.createElement("div");
    card.className = "section-card";
    card.style.boxShadow = "none";
    card.style.borderRadius = "16px";

    const top = document.createElement("div");
    top.style.display = "flex";
    top.style.alignItems = "center";
    top.style.justifyContent = "space-between";
    top.style.gap = "8px";

    const subject = document.createElement("span");
    subject.className = "badge";
    subject.textContent = note.subject;

    const updated = site.makeText("span", "subtle", note.updated);

    top.append(subject, updated);

    const title = site.makeText("p", "card-title", note.title);
    const tags = site.makeText(
      "p",
      "muted",
      `Tags: ${note.tags.join(", ")}`
    );

    card.append(top, title, tags);
    return card;
  });
});
