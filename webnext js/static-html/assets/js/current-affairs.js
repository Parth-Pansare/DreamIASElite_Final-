document.addEventListener("DOMContentLoaded", () => {
  const { currentAffairsItems, currentAffairsArticles } = window.mockData;

  site.renderList("#ca-item-grid", currentAffairsItems, (item) => {
    const card = document.createElement("div");
    card.className = "section-card";

    const title = site.makeText("p", "card-title", item.title);
    title.style.color = "var(--indigo-700)";
    const detail = site.makeText("p", "muted", item.detail);
    detail.style.marginTop = "6px";

    const link = site.buildLink("#articles", "btn", "Open");
    link.style.marginTop = "12px";

    card.append(title, detail, link);
    return card;
  });

  site.renderList("#article-grid", currentAffairsArticles, (article) => {
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

    const read = site.buildLink(
      `current-affairs.html?article=${encodeURIComponent(article.title)}#articles`,
      "btn btn-outline",
      "Read"
    );
    read.style.marginTop = "10px";

    card.append(category, title, meta, read);
    return card;
  });
});
