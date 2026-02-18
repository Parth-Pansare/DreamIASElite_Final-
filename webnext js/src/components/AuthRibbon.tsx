export function AuthRibbon() {
  return (
    <div className="bg-slate-900 px-6 py-2 text-xs font-semibold text-indigo-100">
      <div className="mx-auto flex max-w-6xl items-center justify-between md:px-4">
        <span>Guest mode enabled — hook up real auth to sync progress.</span>
        <div className="hidden items-center gap-2 md:flex">
          <button className="rounded-full border border-indigo-300/40 px-3 py-1 text-indigo-100 transition hover:bg-indigo-800">
            Log in
          </button>
          <button className="rounded-full bg-white px-3 py-1 text-slate-900 transition hover:bg-slate-100">
            Create account
          </button>
        </div>
      </div>
    </div>
  );
}
