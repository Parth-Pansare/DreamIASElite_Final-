import Link from "next/link";
import { testQuestions, testsList } from "@/lib/mockData";
import { TestDemo } from "@/components/TestDemo";

type TestDetailPageProps = {
  params: { id?: string | string[] };
};

export default function TestDetailPage({ params }: TestDetailPageProps) {
  const rawId = params?.id;
  const testId = Array.isArray(rawId) ? rawId[0] : rawId ?? "";
  const test = testsList.find((t) => t.id === testId);

  if (!test) {
    return (
      <div className="mx-auto flex max-w-4xl flex-col gap-6 px-6 py-16 md:px-10 md:py-20">
        <div className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100">
          <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
            Test not found
          </p>
          <p className="mt-2 text-slate-700">
            The requested test is unavailable. Choose another test below.
          </p>
          <div className="mt-4 flex gap-3">
            <Link
              href="/tests#test-list"
              className="rounded-full bg-indigo-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-indigo-700"
            >
              Back to tests
            </Link>
          </div>
        </div>
      </div>
    );
  }

  const questions = testQuestions[test.id] ?? [];

  return (
    <div className="mx-auto flex max-w-6xl flex-col gap-8 px-6 py-16 md:px-10 md:py-20">
      <div className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100">
        <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
          Test detail
        </p>
        <h1 className="mt-2 text-3xl font-semibold text-slate-900">
          {test.title}
        </h1>
        <p className="text-sm text-slate-600">
          {test.subject} · {test.duration} · {test.questions} questions
        </p>
        {test.description && (
          <p className="mt-2 text-slate-700">{test.description}</p>
        )}
        <div className="mt-4 flex gap-3">
          <Link
            href="/tests#test-list"
            className="rounded-full bg-indigo-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-indigo-700"
          >
            Start demo session
          </Link>
          <Link
            href="/tests"
            className="rounded-full border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-800 transition hover:border-indigo-200 hover:text-indigo-700"
          >
            Back to tests
          </Link>
        </div>
      </div>

      <TestDemo questions={questions} testTitle={test.title} />
    </div>
  );
}
