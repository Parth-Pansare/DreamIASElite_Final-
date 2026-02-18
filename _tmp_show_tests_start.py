from pathlib import Path
import sys
lines = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/tests/TestSubjectScreen.kt").read_text(encoding="utf-8").splitlines()
for i in range(min(80, len(lines))):
    sys.stdout.buffer.write(f"{i+1}: {lines[i]}\n".encode("utf-8", errors="replace"))
