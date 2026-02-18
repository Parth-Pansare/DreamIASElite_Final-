from pathlib import Path

lines = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/notes/NotesScreen.kt").read_text(encoding="utf-8").splitlines()
for i, line in enumerate(lines, 1):
    if "clickable" in line and "onFlip" in line:
        for j in range(i-3, i+5):
            if 1 <= j <= len(lines):
                print(f"{j}:{lines[j-1]}")
