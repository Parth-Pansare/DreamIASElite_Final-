import Link from "next/link";
import { pyqPapers, pyqQuestions } from "@/lib/mockData";

export default function PyqPage() {
  return (
    <div className="mx-auto flex max-w-6xl flex-col gap-8 px-6 py-16 md:px-10 md:py-20">
      <div className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100">
        <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
          PYQ
        </p>
        <h1 className="mt-2 text-3xl font-semibold text-slate-900">
          Past Year Questions with explanations.
        </h1>
        <p className="mt-2 text-slate-600">
          Filter by year or subject, review answers, and reinforce concepts.
        </p>
      </div>

      <div id="pyq-list" className="grid gap-6 md:grid-cols-2">
        {pyqPapers.map((item) => (
          <div
            key={item.title}
            className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100"
          >
            <p className="text-sm font-semibold text-indigo-700">
              {item.title}
            </p>
            <p className="mt-1 text-slate-600">{item.detail}</p>
            <div className="mt-4 flex gap-3 text-sm font-semibold">
              <Link
                href={`/pyq?paper=${encodeURIComponent(item.title)}#pyq-samples`}
                className="rounded-full bg-indigo-600 px-4 py-2 text-white transition hover:bg-indigo-700"
              >
                Open set
              </Link>
              <Link
                href="#pyq-samples"
                className="rounded-full border border-slate-200 px-4 py-2 text-slate-800 transition hover:border-indigo-200 hover:text-indigo-700"
              >
                View explanations
              </Link>
            </div>
          </div>
        ))}
      </div>

      <div
        id="pyq-samples"
        className="rounded-3xl bg-white p-6 shadow-md ring-1 ring-slate-100"
      >
        <div className="flex items-center justify-between">
          <div>
            <p className="text-sm font-semibold text-indigo-600">
              Sample questions
            </p>
            <p className="text-sm text-slate-600">
              Answer snippets for quick review.
            </p>
          </div>
          <Link
            href="/tests#test-grid"
            className="text-sm font-semibold text-indigo-700 hover:text-indigo-800"
          >
            Drill MCQs
          </Link>
        </div>
        <div className="mt-4 space-y-3">
          {pyqQuestions.map((q) => (
            <div
              key={q.prompt}
              className="rounded-xl bg-slate-50 px-4 py-3 ring-1 ring-slate-100"
            >
              <p className="text-xs font-semibold uppercase tracking-wide text-indigo-700">
                {q.title}
              </p>
              <p className="font-semibold text-slate-900">{q.prompt}</p>
              <p className="text-sm text-slate-700">{q.answer}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
