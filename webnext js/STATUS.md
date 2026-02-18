# Dream IAS Elite — Web Notes

Last updated: current session

## Stack
- Next.js (App Router) + TypeScript + Tailwind
- Prisma ORM (PostgreSQL) — schema created, migrations not run yet
- Package manager: pnpm

## What’s built
- Layout with header/footer/nav + guest auth ribbon
- Landing page with CTA links to tests/dashboard/current affairs
- Sections with placeholder/demo data:
  - Dashboard (`/dashboard`) — highlights, subject chips, next tests, CA highlights
  - Tests (`/tests`) — subject cards + demo test bank, links to detail pages
  - Test detail (`/tests/[id]`) — shows test info and interactive demo questions with submit/reset (data from `testQuestions`)
  - PYQ (`/pyq`) — sample papers and snippets
  - Notes (`/notes`) — folders + recent notes
  - Current Affairs (`/current-affairs`) — featured items + articles

## Data
- Demo data only (no real UPSC content). Stored in `src/lib/mockData.ts`.
- Prisma models defined in `prisma/schema.prisma` (User, Test/TestSession/Question, Note, Flashcard, Article, PyqPaper/PyqQuestion, Subject enum).
- DB URL set in `.env` for local Postgres: `postgresql://postgres:postgres@localhost:5432/dreamias?schema=public`.
- Prisma client helper: `src/lib/prisma.ts`.

## Commands
- Dev server: `pnpm dev` (open http://localhost:3000)
- Lint: `pnpm lint`
- Prisma format: `pnpm prisma format`
- When ready for DB: ensure Postgres container is running, then `pnpm prisma migrate dev --name init` (not run yet).

## Key files
- Pages: `src/app/page.tsx`, `src/app/dashboard/page.tsx`, `src/app/tests/page.tsx`, `src/app/tests/[id]/page.tsx`, `src/app/pyq/page.tsx`, `src/app/notes/page.tsx`, `src/app/current-affairs/page.tsx`
- Components: `src/components/AuthRibbon.tsx`, `src/components/TestDemo.tsx`
- Data: `src/lib/mockData.ts`
- Layout/styles: `src/app/layout.tsx`, `src/app/globals.css`
- Prisma: `prisma/schema.prisma`, `prisma.config.ts`

## Next steps (suggested)
- Run initial migration and swap pages to fetch from Prisma instead of mocks.
- Add seed/import scripts for real UPSC tests/PYQs/notes/CA when available.
- Build route handlers/server actions for tests, notes, CA, and PYQ.
- Add auth flow if needed; replace guest ribbon.
