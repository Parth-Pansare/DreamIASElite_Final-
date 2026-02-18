"use client";

import { useMemo, useState } from "react";

type DemoQuestion = {
  prompt: string;
  options: string[];
  answer: string;
};

type Props = {
  questions: DemoQuestion[];
  testTitle: string;
};

export function TestDemo({ questions, testTitle }: Props) {
  const [selected, setSelected] = useState<Record<number, string>>({});
  const [submitted, setSubmitted] = useState(false);

  const score = useMemo(() => {
    if (!submitted) return 0;
    return questions.reduce((acc, q, idx) => {
      return acc + (selected[idx] === q.answer ? 1 : 0);
    }, 0);
  }, [questions, selected, submitted]);

  const allAnswered =
    questions.length > 0 &&
    questions.every((_, idx) => typeof selected[idx] === "string");

  return (
    <div className="rounded-3xl bg-white p-6 shadow-md ring-1 ring-slate-100">
      <div className="flex flex-col gap-2 md:flex-row md:items-center md:justify-between">
        <div>
          <p className="text-sm font-semibold text-indigo-600">
            Demo session
          </p>
          <p className="text-sm text-slate-600">
            {questions.length} sample questions from {testTitle}.
          </p>
        </div>
        <div className="flex gap-2">
          <button
            className="rounded-full bg-indigo-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-indigo-700 disabled:cursor-not-allowed disabled:bg-indigo-300"
            onClick={() => setSubmitted(true)}
            disabled={submitted || !questions.length || !allAnswered}
          >
            Submit
          </button>
          <button
            className="rounded-full border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-800 transition hover:border-indigo-200 hover:text-indigo-700"
            onClick={() => {
              setSelected({});
              setSubmitted(false);
            }}
          >
            Reset
          </button>
        </div>
      </div>

      {submitted && (
        <div className="mt-4 rounded-xl bg-emerald-50 px-4 py-3 text-sm text-emerald-800 ring-1 ring-emerald-100">
          Score: {score} / {questions.length}
        </div>
      )}

      <div className="mt-4 space-y-4">
        {questions.map((q, idx) => {
          const isCorrect = submitted && selected[idx] === q.answer;
          const isWrong = submitted && selected[idx] && selected[idx] !== q.answer;
          return (
            <div
              key={q.prompt}
              className="rounded-2xl bg-slate-50 px-4 py-4 ring-1 ring-slate-100"
            >
              <p className="text-xs font-semibold uppercase tracking-wide text-indigo-700">
                Q{idx + 1}
              </p>
              <p className="font-semibold text-slate-900">{q.prompt}</p>
              <div className="mt-2 space-y-2 text-sm text-slate-800">
                {q.options.map((opt) => {
                  const selectedOpt = selected[idx] === opt;
                  const showCorrect = submitted && opt === q.answer;
                  return (
                    <button
                      key={opt}
                      onClick={() =>
                        setSelected((prev) => ({
                          ...prev,
                          [idx]: opt,
                        }))
                      }
                      className={`flex w-full items-start gap-2 rounded-lg border px-3 py-2 text-left transition ${
                        selectedOpt
                          ? "border-indigo-300 bg-white shadow-sm"
                          : "border-slate-200 bg-white/70 hover:border-indigo-200"
                      } ${submitted && showCorrect ? "ring-1 ring-emerald-200" : ""}`}
                      disabled={submitted}
                    >
                      <span className="mt-1 h-2 w-2 rounded-full bg-slate-300" />
                      <span className="flex-1">{opt}</span>
                      {submitted && showCorrect && (
                        <span className="text-xs font-semibold text-emerald-700">
                          Correct
                        </span>
                      )}
                      {submitted && selectedOpt && !showCorrect && (
                        <span className="text-xs font-semibold text-rose-600">
                          Your choice
                        </span>
                      )}
                    </button>
                  );
                })}
              </div>
              {submitted && (
                <p
                  className={`mt-2 text-sm font-semibold ${
                    isCorrect
                      ? "text-emerald-700"
                      : isWrong
                      ? "text-rose-700"
                      : "text-slate-600"
                  }`}
                >
                  Answer: {q.answer}
                </p>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}
