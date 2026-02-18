from pathlib import Path
import sys
lines = Path("app/src/main/java/com/app/dreamiaselite/MainActivity.kt").read_text(encoding="utf-8").splitlines()
start=300
end=335
for i in range(start-1, min(end, len(lines))):
    sys.stdout.buffer.write(f"{i+1}: {lines[i]}\n".encode("utf-8", errors="replace"))
