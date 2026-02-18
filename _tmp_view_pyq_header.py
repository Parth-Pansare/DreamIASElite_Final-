from pathlib import Path
import sys

lines = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/pyq/PyqScreen.kt").read_text(encoding="utf-8").splitlines()
for i in range(0, min(120, len(lines))):
    sys.stdout.buffer.write(f"{i+1}: {lines[i]}\n".encode("utf-8", errors="replace"))
