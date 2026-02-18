import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import { AuthRibbon } from "@/components/AuthRibbon";

const navLinks = [
  { href: "/", label: "Home" },
  { href: "/dashboard", label: "Dashboard" },
  { href: "/tests", label: "Tests" },
  { href: "/pyq", label: "PYQ" },
  { href: "/notes", label: "Notes" },
  { href: "/current-affairs", label: "Current Affairs" },
];

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Dream IAS Elite",
  description: "UPSC companion for tests, current affairs, PYQs, notes, and flashcards.",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
      >
        <AuthRibbon />
        <div className="min-h-screen bg-gradient-to-b from-slate-50 via-white to-slate-100 text-slate-900">
          <header className="sticky top-0 z-20 border-b border-slate-200/80 bg-white/90 backdrop-blur">
            <div className="mx-auto flex max-w-6xl items-center justify-between px-6 py-3 md:px-10">
              <div className="text-lg font-semibold text-indigo-700">
                Dream IAS Elite
              </div>
              <nav className="flex items-center gap-4 text-sm font-medium text-slate-700">
                {navLinks.map((link) => (
                  <a
                    key={link.href}
                    href={link.href}
                    className="rounded-full px-3 py-2 transition hover:bg-slate-100 hover:text-indigo-700"
                  >
                    {link.label}
                  </a>
                ))}
              </nav>
              <div className="hidden items-center gap-3 md:flex">
                <button className="rounded-full border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-800 transition hover:border-indigo-200 hover:text-indigo-700">
                  Log in
                </button>
                <button className="rounded-full bg-indigo-600 px-4 py-2 text-sm font-semibold text-white shadow-sm transition hover:bg-indigo-700">
                  Start now
                </button>
              </div>
            </div>
          </header>

          <main>{children}</main>

          <footer className="border-t border-slate-200 bg-white">
            <div className="mx-auto flex max-w-6xl flex-col gap-3 px-6 py-8 text-sm text-slate-600 md:flex-row md:items-center md:justify-between md:px-10">
              <div className="font-semibold text-slate-800">Dream IAS Elite</div>
              <div className="flex flex-wrap gap-4">
                <span>UPSC companion for tests, notes, PYQs, and current affairs.</span>
                <span className="text-slate-400">Stay consistent. Stay ahead.</span>
              </div>
            </div>
          </footer>
        </div>
      </body>
    </html>
  );
}
