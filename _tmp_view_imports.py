from pathlib import Path

lines = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/notes/NotesScreen.kt").read_text(encoding="utf-8").splitlines()
start = 1
end = 140
for i in range(start - 1, min(end, len(lines))):
    print(f"{i+1}: {lines[i]}")
