import Link from "next/link";
import { noteFolders, notesCards, recentNotes } from "@/lib/mockData";

export default function NotesPage() {
  return (
    <div className="mx-auto flex max-w-6xl flex-col gap-8 px-6 py-16 md:px-10 md:py-20">
      <div className="rounded-3xl bg-white p-8 shadow-md ring-1 ring-slate-100">
        <p className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
          Notes & Flashcards
        </p>
        <h1 className="mt-2 text-3xl font-semibold text-slate-900">
          Organize, tag, and revise quickly.
        </h1>
        <p className="mt-2 text-slate-600">
          Folder grid by subject, recent notes list, and flashcards for rapid revision.
        </p>
      </div>

      <div className="grid gap-6 md:grid-cols-3">
        {notesCards.map((card) => (
          <div
            key={card.title}
            className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100"
          >
            <p className="text-sm font-semibold text-indigo-700">
              {card.title}
            </p>
            <p className="mt-2 text-slate-600">{card.detail}</p>
            <Link
              href="#notes-list"
              className="mt-4 inline-block rounded-full bg-indigo-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-indigo-700"
            >
              View
            </Link>
          </div>
        ))}
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <div
          id="notes-folders"
          className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100"
        >
          <div className="flex items-center justify-between">
            <p className="text-sm font-semibold text-indigo-600">Folders</p>
            <Link
              href="#notes-list"
              className="text-sm font-semibold text-indigo-700 hover:text-indigo-800"
            >
              Recent
            </Link>
          </div>
          <div className="mt-4 grid grid-cols-2 gap-3 text-sm text-slate-800 sm:grid-cols-3">
            {noteFolders.map((folder) => (
              <div
                key={folder.name}
                className="rounded-xl bg-slate-50 px-4 py-3 ring-1 ring-slate-100"
              >
                <p className="font-semibold text-indigo-700">{folder.name}</p>
                <p className="text-xs text-slate-600">{folder.count} notes</p>
              </div>
            ))}
          </div>
        </div>

        <div
          id="notes-list"
          className="rounded-2xl bg-white p-6 shadow-md ring-1 ring-slate-100"
        >
          <div className="flex items-center justify-between">
            <p className="text-sm font-semibold text-indigo-600">
              Recent notes
            </p>
            <Link
              href="/current-affairs#articles"
              className="text-sm font-semibold text-indigo-700 hover:text-indigo-800"
            >
              Read CA
            </Link>
          </div>
          <div className="mt-4 space-y-3">
            {recentNotes.map((note) => (
              <div
                key={note.id}
                className="rounded-xl bg-slate-50 px-4 py-3 ring-1 ring-slate-100"
              >
                <div className="flex items-center justify-between text-sm">
                  <span className="rounded-full bg-indigo-100 px-3 py-1 font-semibold text-indigo-700">
                    {note.subject}
                  </span>
                  <span className="text-xs text-slate-500">{note.updated}</span>
                </div>
                <p className="mt-1 font-semibold text-slate-900">
                  {note.title}
                </p>
                <p className="text-xs text-slate-600">
                  Tags: {note.tags.join(", ")}
                </p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
