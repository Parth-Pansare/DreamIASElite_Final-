import Link from "next/link";
import { testSubjects, testsList } from "@/lib/mockData";

export default function TestsPage() {
  return (
    <div className="mx-auto flex max-w-6xl flex-col gap-8 px-6 py-16 md:px-10 md:py-20">
      <div className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100">
        <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
          Tests
        </p>
        <h1 className="mt-2 text-3xl font-semibold text-slate-900">
          Timed drills with results and explanations.
        </h1>
        <p className="mt-2 text-slate-600">
          Launch subject tests, bookmark questions, and review answers instantly.
        </p>
      </div>

      <div id="test-grid" className="grid gap-6 md:grid-cols-3">
        {testSubjects.map((subject) => {
          const firstMatch = testsList.find(
            (t) => t.subject.toLowerCase() === subject.name.toLowerCase()
          );
          const startHref = firstMatch
            ? `/tests/${firstMatch.id}`
            : "/tests#test-list";
          return (
            <div
              key={subject.name}
              className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100"
            >
              <p className="text-sm font-semibold text-indigo-700">
                {subject.name}
              </p>
              <p className="mt-1 text-slate-600">{subject.detail}</p>
              <div className="mt-4 flex gap-3 text-sm font-semibold">
                <Link
                    href={startHref}
                    className="rounded-full bg-indigo-600 px-4 py-2 text-white transition hover:bg-indigo-700"
                  >
                    Start test
                  </Link>
                  <Link
                    href="#test-list"
                    className="rounded-full border border-slate-200 px-4 py-2 text-slate-800 transition hover:border-indigo-200 hover:text-indigo-700"
                  >
                    Units
                  </Link>
              </div>
            </div>
          );
        })}
      </div>

      <div
        id="test-list"
        className="rounded-3xl bg-white p-6 shadow-md ring-1 ring-slate-100"
      >
        <div className="flex items-center justify-between">
          <div>
            <p className="text-sm font-semibold text-indigo-600">
              Demo test bank
            </p>
            <p className="text-sm text-slate-600">
              10 questions · 10 minutes · instant results
            </p>
          </div>
          <Link
            href="/dashboard"
            className="text-sm font-semibold text-indigo-700 hover:text-indigo-800"
          >
            Back to dashboard
          </Link>
        </div>
        <div className="mt-4 divide-y divide-slate-100">
          {testsList.map((test) => (
            <div
              key={test.id}
              className="flex flex-col gap-2 py-3 md:flex-row md:items-center md:justify-between"
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
                {test.description && (
                  <p className="text-sm text-slate-500">{test.description}</p>
                )}
              </div>
              <Link
                href={`/tests/${test.id}`}
                className="w-fit rounded-full bg-indigo-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-indigo-700"
              >
                Start
              </Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
