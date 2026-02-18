from pathlib import Path
import re
lines = Path("app/src/main/java/com/app/dreamiaselite/MainActivity.kt").read_text(encoding="utf-8").splitlines()
for i, l in enumerate(lines, 1):
    if re.search(r"drawer|offline|download", l, re.IGNORECASE):
        print(f"{i}: {l}")
