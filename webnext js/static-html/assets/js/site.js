(() => {
  const qs = (selector) => document.querySelector(selector);
  const qsa = (selector) => Array.from(document.querySelectorAll(selector));

  const setActiveNav = () => {
    const page = document.body.dataset.page;
    if (!page) return;
    qsa("[data-nav]").forEach((link) => {
      const key = link.getAttribute("data-nav");
      if (key === page) {
        link.classList.add("active");
      }
    });
  };

  const renderList = (target, items, builder) => {
    const container = typeof target === "string" ? qs(target) : target;
    if (!container || !Array.isArray(items)) return;
    container.innerHTML = "";
    items.forEach((item) => container.appendChild(builder(item)));
  };

  const makeText = (tag, className, text) => {
    const el = document.createElement(tag);
    if (className) el.className = className;
    el.textContent = text;
    return el;
  };

  const getParam = (key) =>
    new URLSearchParams(window.location.search || "").get(key);

  const formatTestMeta = (test) =>
    `${test.questions} questions · ${test.duration}`;

  const buildLink = (href, className, text) => {
    const a = document.createElement("a");
    a.href = href;
    a.className = className;
    a.textContent = text;
    return a;
  };

  window.site = {
    qs,
    qsa,
    setActiveNav,
    renderList,
    makeText,
    buildLink,
    getParam,
    formatTestMeta,
  };

  document.addEventListener("DOMContentLoaded", setActiveNav);
})();
