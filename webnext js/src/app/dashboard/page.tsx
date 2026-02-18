import Link from "next/link";
import {
  dashboardHighlights,
  subjects,
  testsList,
  currentAffairsArticles,
} from "@/lib/mockData";

export default function DashboardPage() {
  return (
    <div className="mx-auto flex max-w-6xl flex-col gap-8 px-6 py-16 md:px-10 md:py-20">
      <div className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100">
        <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
          Dashboard
        </p>
        <h1 className="mt-2 text-3xl font-semibold text-slate-900">
          Your daily UPSC cockpit.
        </h1>
        <p className="mt-2 text-slate-600">
          Quick view of streaks, daily tasks, and subject shortcuts.
        </p>
        <div className="mt-6 grid gap-4 md:grid-cols-3">
          {dashboardHighlights.map((item) => (
            <div
              key={item.title}
              className="rounded-2xl bg-slate-50 px-4 py-4 ring-1 ring-slate-100"
            >
              <p className="text-sm font-semibold text-indigo-700">
                {item.title}
              </p>
              <p className="text-2xl font-semibold text-slate-900">
                {item.value}
              </p>
              <p className="text-sm text-slate-600">{item.detail}</p>
            </div>
          ))}
        </div>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <div className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100">
          <p className="text-sm font-semibold text-indigo-600">Subjects</p>
          <div className="mt-4 grid grid-cols-2 gap-3 text-sm text-slate-800 sm:grid-cols-3">
            {subjects.map((subject) => (
              <div
                key={subject}
                className="rounded-xl bg-slate-50 px-4 py-3 ring-1 ring-slate-100"
              >
                {subject}
              </div>
            ))}
          </div>
        </div>
        <div className="rounded-2xl bg-slate-900 p-6 text-white shadow-md">
          <p className="text-sm font-semibold uppercase tracking-wide text-indigo-200">
            Quick start
          </p>
          <h3 className="mt-2 text-xl font-semibold">Launch a 10-minute drill</h3>
          <p className="mt-2 text-indigo-100">
            Timed MCQs with bookmarks and instant explanations.
          </p>
          <Link
            href="/tests/hist-full"
            className="mt-4 w-fit rounded-full bg-white px-5 py-3 text-sm font-semibold text-slate-900 transition hover:bg-slate-100"
          >
            Start test
          </Link>
        </div>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <div className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-semibold text-indigo-600">Next tests</p>
              <p className="text-slate-600 text-sm">Pick a test to begin.</p>
            </div>
            <Link
              href="/tests#test-grid"
              className="text-sm font-semibold text-indigo-700 hover:text-indigo-800"
            >
              View all
            </Link>
          </div>
          <div className="mt-4 space-y-3">
            {testsList.slice(0, 3).map((test) => (
              <div
                key={test.id}
                className="flex items-center justify-between rounded-xl bg-slate-50 px-4 py-3 ring-1 ring-slate-100"
              >
                <div>
                  <p className="text-sm font-semibold text-indigo-700">
                    {test.subject}
                  </p>
                  <p className="text-sm text-slate-800">{test.title}</p>
                </div>
                <Link
                  href={`/tests?start=${test.id}#test-grid`}
                  className="rounded-full bg-indigo-600 px-3 py-2 text-xs font-semibold text-white transition hover:bg-indigo-700"
                >
                  Start
                </Link>
              </div>
            ))}
          </div>
        </div>
        <div className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-semibold text-indigo-600">
                Current affairs
              </p>
              <p className="text-slate-600 text-sm">Fresh highlights.</p>
            </div>
            <Link
              href="/current-affairs#articles"
              className="text-sm font-semibold text-indigo-700 hover:text-indigo-800"
            >
              View all
            </Link>
          </div>
          <div className="mt-4 space-y-3">
            {currentAffairsArticles.map((article) => (
              <div
                key={article.title}
                className="rounded-xl bg-slate-50 px-4 py-3 text-sm text-slate-800 ring-1 ring-slate-100"
              >
                <p className="font-semibold text-indigo-700">
                  {article.category}
                </p>
                <p className="font-semibold text-slate-900">{article.title}</p>
                <p className="text-xs text-slate-600">
                  {article.date} · {article.readTime}
                </p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
