from pathlib import Path

lines = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/notes/NotesScreen.kt").read_text(encoding="utf-8").splitlines()
start = 580
end = 650
for i in range(start, min(end, len(lines))):
    print(f"{i+1}: {lines[i]}")
