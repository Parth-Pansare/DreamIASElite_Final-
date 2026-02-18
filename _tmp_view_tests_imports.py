from pathlib import Path
import sys

imports = []
with open("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/tests/TestSubjectScreen.kt", "r", encoding="utf-8") as f:
    for line in f:
        if line.strip() == "":
            break
        imports.append(line.rstrip("\n"))

for i, line in enumerate(imports, 1):
    sys.stdout.buffer.write(f"{i}: {line}\n".encode("utf-8", errors="replace"))
