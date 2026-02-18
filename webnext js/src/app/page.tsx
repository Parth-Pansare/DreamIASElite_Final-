import Link from "next/link";
import {
  currentAffairsArticles,
  testsList,
} from "@/lib/mockData";

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-slate-50 via-white to-slate-100 text-slate-900">
      <main className="mx-auto flex max-w-6xl flex-col gap-16 px-6 py-16 md:px-10 md:py-20">
        <section className="rounded-3xl bg-white p-10 shadow-lg ring-1 ring-slate-100 md:flex md:items-center md:justify-between md:gap-10">
          <div className="flex flex-col gap-6">
            <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
              Dream IAS Elite
            </p>
            <h1 className="text-3xl font-semibold leading-tight md:text-4xl">
              Your UPSC companion for tests, notes, PYQs, and current affairs.
            </h1>
            <p className="max-w-2xl text-lg text-slate-600">
              Track progress, drill subjects, and stay on top of monthly current affairs with a single, focused workspace.
            </p>
            <div className="flex flex-wrap gap-3">
              <Link
                href="/dashboard"
                className="rounded-full bg-indigo-600 px-6 py-3 text-sm font-semibold text-white shadow-lg shadow-indigo-200 transition hover:bg-indigo-700"
              >
                Go to dashboard
              </Link>
              <Link
                href="/tests/hist-full"
                className="rounded-full border border-slate-200 px-6 py-3 text-sm font-semibold text-slate-800 transition hover:border-indigo-200 hover:text-indigo-700"
              >
                Start a test
              </Link>
            </div>
            <div className="grid grid-cols-2 gap-4 text-sm text-slate-700 sm:grid-cols-3 md:grid-cols-4">
              {[
                "Daily MCQ drills",
                "Monthly CA tests",
                "PYQ explorer",
                "Flashcards & notes",
              ].map((item) => (
                <div
                  key={item}
                  className="rounded-xl bg-slate-50 px-4 py-3 ring-1 ring-slate-100"
                >
                  {item}
                </div>
              ))}
            </div>
          </div>
          <div className="mt-10 grid w-full max-w-sm gap-3 rounded-2xl bg-slate-900 p-6 text-white md:mt-0">
            <p className="text-xs uppercase tracking-[0.2em] text-indigo-200">
              Today&apos;s plan
            </p>
            <div className="grid gap-3 text-sm">
              <div className="flex items-center justify-between rounded-xl bg-white/5 px-4 py-3">
                <span>History MCQs</span>
                <span className="rounded-full bg-indigo-500/80 px-3 py-1 text-xs font-semibold">
                  20 Qs
                </span>
              </div>
              <div className="flex items-center justify-between rounded-xl bg-white/5 px-4 py-3">
                <span>Current Affairs</span>
                <span className="text-indigo-100">12 min</span>
              </div>
              <div className="flex items-center justify-between rounded-xl bg-white/5 px-4 py-3">
                <span>PYQ set</span>
                <span className="text-indigo-100">Paper 2023</span>
              </div>
            </div>
            <div className="pt-2 text-xs text-indigo-100">
              Stay consistent—streaks and scores sync across dashboard and tests.
            </div>
          </div>
        </section>

        <section className="grid gap-6 md:grid-cols-3">
          {[
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
          ].map((item) => (
            <div
              key={item.title}
              className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100"
            >
              <p className="text-sm font-semibold text-indigo-600">
                {item.title}
              </p>
              <p className="mt-3 text-slate-700">{item.text}</p>
            </div>
          ))}
        </section>

        <section
          id="tests-preview"
          className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100"
        >
          <div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
            <div>
              <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
                Tests preview
              </p>
              <h2 className="text-2xl font-semibold text-slate-900">
                Launch a 10-minute drill.
              </h2>
              <p className="mt-2 max-w-2xl text-slate-600">
                Timed MCQs with explanations. Pick a subject to start.
              </p>
            </div>
            <Link
              href="/tests#test-grid"
              className="rounded-full bg-slate-900 px-6 py-3 text-sm font-semibold text-white transition hover:bg-slate-800"
            >
              View all tests
            </Link>
          </div>
          <div className="mt-6 grid gap-4 md:grid-cols-2">
            {testsList.slice(0, 4).map((test) => (
              <div
                key={test.id}
                className="flex items-center justify-between rounded-xl bg-slate-50 px-5 py-4 ring-1 ring-slate-100"
              >
                <div>
                  <p className="text-sm font-semibold text-indigo-700">
                    {test.subject}
                  </p>
                  <p className="text-base font-semibold text-slate-900">
                    {test.title}
                  </p>
                  <p className="text-sm text-slate-600">
                    {test.questions} questions · {test.duration}
                  </p>
                </div>
                <Link
                  href={`/tests/${test.id}`}
                  className="rounded-full bg-indigo-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-indigo-700"
                >
                  Start
                </Link>
              </div>
            ))}
          </div>
        </section>

        <section className="rounded-3xl bg-slate-900 px-8 py-10 text-white shadow-lg">
          <div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
            <div>
              <p className="text-sm font-semibold uppercase tracking-wide text-indigo-200">
                Get started
              </p>
              <h3 className="text-2xl font-semibold">
                Ready to launch your next study session?
              </h3>
              <p className="mt-2 text-slate-200">
                Sign in, pick a subject, and run a 10-minute drill to keep your streak alive.
              </p>
            </div>
            <div className="flex gap-3">
              <Link
                href="/dashboard"
                className="rounded-full bg-white px-5 py-3 text-sm font-semibold text-slate-900 transition hover:bg-slate-100"
              >
                Open dashboard
              </Link>
              <Link
                href="/tests#test-grid"
                className="rounded-full border border-white/40 px-5 py-3 text-sm font-semibold text-white transition hover:bg-white/10"
              >
                Start a test
              </Link>
            </div>
          </div>
        </section>

        <section
          id="current-affairs-preview"
          className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100"
        >
          <div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
            <div>
              <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
                Current Affairs & PYQ
              </p>
              <h2 className="text-2xl font-semibold text-slate-900">
                Weekly highlights and past-year papers in one place.
              </h2>
              <p className="mt-2 max-w-2xl text-slate-600">
                Browse curated articles, start monthly CA tests, and drill PYQ sets with explanations.
              </p>
            </div>
            <Link
              href="/current-affairs#articles"
              className="rounded-full bg-slate-900 px-6 py-3 text-sm font-semibold text-white transition hover:bg-slate-800"
            >
              View current affairs
            </Link>
          </div>
          <div className="mt-6 grid gap-4 md:grid-cols-3">
            {currentAffairsArticles.map((article) => (
              <div
                key={article.title}
                className="rounded-xl bg-slate-50 px-4 py-3 text-slate-800 ring-1 ring-slate-100"
              >
                <p className="text-sm font-semibold text-indigo-700">
                  {article.category}
                </p>
                <p className="font-semibold text-slate-900">{article.title}</p>
                <p className="text-sm text-slate-600">
                  {article.date} · {article.readTime}
                </p>
              </div>
            ))}
          </div>
        </section>
      </main>
    </div>
  );
}
