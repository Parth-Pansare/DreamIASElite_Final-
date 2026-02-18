document.addEventListener("DOMContentLoaded", () => {
  const { testsList, testQuestions } = window.mockData;
  const container = site.qs("#test-page");
  if (!container) return;

  const paramId = site.getParam("id");
  const fallbackId = testsList[0] ? testsList[0].id : "";
  const testId = paramId || fallbackId;
  const test = testsList.find((t) => t.id === testId);

  if (!test) {
    const empty = document.createElement("div");
    empty.className = "section-card";
    empty.innerHTML = `
      <div class="section-title">Test not found</div>
      <p class="muted">The requested test is unavailable. Choose another test below.</p>
      <div class="cta-row" style="margin-top:12px;">
        <a class="btn" href="tests.html#test-list">Back to tests</a>
      </div>
    `;
    container.appendChild(empty);
    return;
  }

  const questions = testQuestions[test.id] || [];
  const selected = {};
  let submitted = false;

  const headerCard = document.createElement("div");
  headerCard.className = "section-card";
  headerCard.innerHTML = `
    <div class="section-title">Test detail</div>
    <h1 class="section-lead">${test.title}</h1>
    <p class="muted">${test.subject} · ${test.duration} · ${test.questions} questions</p>
    <p class="muted">${test.description || ""}</p>
  `;

  const actionRow = document.createElement("div");
  actionRow.className = "cta-row";
  actionRow.style.marginTop = "12px";

  const submitBtn = document.createElement("button");
  submitBtn.className = "btn";
  submitBtn.textContent = "Submit";

  const resetBtn = document.createElement("button");
  resetBtn.className = "btn btn-outline";
  resetBtn.textContent = "Reset";

  actionRow.append(submitBtn, resetBtn);
  headerCard.appendChild(actionRow);
  container.appendChild(headerCard);

  const scoreBar = document.createElement("div");
  scoreBar.className = "status-bar";
  scoreBar.textContent = "Select answers to see your score.";
  scoreBar.style.display = "none";
  headerCard.appendChild(scoreBar);

  const questionsWrap = document.createElement("div");
  questionsWrap.className = "section-card";
  questionsWrap.style.marginTop = "16px";

  const questionList = document.createElement("div");
  questionList.className = "card-stack";
  questionsWrap.appendChild(questionList);
  container.appendChild(questionsWrap);

  if (!questions.length) {
    const empty = document.createElement("div");
    empty.className = "empty";
    empty.textContent = "No questions available for this test.";
    questionList.appendChild(empty);
    submitBtn.disabled = true;
  }

  const blocks = [];

  questions.forEach((q, idx) => {
    const card = document.createElement("div");
    card.className = "section-card";
    card.style.boxShadow = "none";
    card.style.borderRadius = "16px";

    const label = document.createElement("p");
    label.className = "pill-small";
    label.textContent = `Q${idx + 1}`;

    const prompt = site.makeText("p", "card-title", q.prompt);

    const optionsWrap = document.createElement("div");
    optionsWrap.className = "card-stack";

    const btns = [];
    q.options.forEach((opt) => {
      const btn = document.createElement("button");
      btn.type = "button";
      btn.className = "option-button";
      btn.dataset.option = opt;
      btn.innerHTML = `<span style="display:inline-block;width:8px;height:8px;border-radius:999px;background:var(--slate-300);margin-top:4px;"></span><span style="flex:1;">${opt}</span>`;
      btn.addEventListener("click", () => {
        if (submitted) return;
        selected[idx] = opt;
        updateButtons();
        updateSubmitState();
      });
      btns.push(btn);
      optionsWrap.appendChild(btn);
    });

    const answerLine = document.createElement("p");
    answerLine.className = "muted";
    answerLine.style.display = "none";

    card.append(label, prompt, optionsWrap, answerLine);
    questionList.appendChild(card);
    blocks.push({ buttons: btns, answerLine, question: q, index: idx });
  });

  const updateSubmitState = () => {
    const allAnswered =
      questions.length > 0 &&
      questions.every((_, idx) => typeof selected[idx] === "string");
    submitBtn.disabled = submitted || !allAnswered;
  };

  const updateButtons = () => {
    blocks.forEach((block) => {
      block.buttons.forEach((btn) => {
        const isChosen = selected[block.index] === btn.dataset.option;
        btn.classList.toggle("active", isChosen);
        btn.classList.remove("correct", "wrong");
        if (submitted) {
          const isCorrect = btn.dataset.option === block.question.answer;
          if (isCorrect) {
            btn.classList.add("correct");
          }
          if (isChosen && !isCorrect) {
            btn.classList.add("wrong");
          }
        }
      });
      if (submitted) {
        block.answerLine.style.display = "block";
        block.answerLine.textContent = `Answer: ${block.question.answer}`;
      } else {
        block.answerLine.style.display = "none";
      }
    });

    if (submitted) {
      const score = questions.reduce(
        (acc, q, idx) => acc + (selected[idx] === q.answer ? 1 : 0),
        0
      );
      scoreBar.style.display = "inline-flex";
      scoreBar.textContent = `Score: ${score} / ${questions.length}`;
    } else {
      scoreBar.style.display = "none";
    }
  };

  submitBtn.addEventListener("click", () => {
    submitted = true;
    updateSubmitState();
    updateButtons();
  });

  resetBtn.addEventListener("click", () => {
    submitted = false;
    Object.keys(selected).forEach((key) => delete selected[key]);
    updateSubmitState();
    updateButtons();
  });

  updateSubmitState();
  updateButtons();
});
