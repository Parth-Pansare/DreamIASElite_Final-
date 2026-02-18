import Link from "next/link";
import {
  currentAffairsArticles,
  currentAffairsItems,
} from "@/lib/mockData";

export default function CurrentAffairsPage() {
  return (
    <div className="mx-auto flex max-w-6xl flex-col gap-8 px-6 py-16 md:px-10 md:py-20">
      <div className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100">
        <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
          Current Affairs
        </p>
        <h1 className="mt-2 text-3xl font-semibold text-slate-900">
          Curated articles and tests in one feed.
        </h1>
        <p className="mt-2 text-slate-600">
          Filter by category, read summaries, and launch monthly CA tests.
        </p>
      </div>

      <div className="grid gap-6 md:grid-cols-3">
        {currentAffairsItems.map((item) => (
          <div
            key={item.title}
            className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100"
          >
            <p className="text-sm font-semibold text-indigo-700">
              {item.title}
            </p>
            <p className="mt-2 text-slate-600">{item.detail}</p>
            <Link
              href="#articles"
              className="mt-4 inline-block rounded-full bg-indigo-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-indigo-700"
            >
              Open
            </Link>
          </div>
        ))}
      </div>

      <div
        id="articles"
        className="rounded-3xl bg-white p-6 shadow-md ring-1 ring-slate-100"
      >
        <div className="flex items-center justify-between">
          <div>
            <p className="text-sm font-semibold text-indigo-600">
              Latest articles
            </p>
            <p className="text-sm text-slate-600">
              Tap an item to read the summary.
            </p>
          </div>
          <Link
            href="/pyq#pyq-list"
            className="text-sm font-semibold text-indigo-700 hover:text-indigo-800"
          >
            Switch to PYQ
          </Link>
        </div>
        <div className="mt-4 grid gap-3 md:grid-cols-3">
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
              <Link
                href={`/current-affairs?article=${encodeURIComponent(article.title)}#articles`}
                className="mt-2 inline-block text-sm font-semibold text-indigo-700 hover:text-indigo-800"
              >
                Read
              </Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
