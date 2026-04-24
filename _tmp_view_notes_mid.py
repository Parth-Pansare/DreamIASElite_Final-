from pathlib import Path
import sys

lines = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/notes/NotesScreen.kt").read_text(encoding="utf-8").splitlines()
start = 260
end = 520

for i in range(start - 1, min(end, len(lines))):
    line = f"{i+1}: {lines[i]}"
    sys.stdout.buffer.write((line + "\n").encode("utf-8", errors="replace"))
