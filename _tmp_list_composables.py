from pathlib import Path
import re

text = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/notes/NotesScreen.kt").read_text(encoding="utf-8")
for match in re.finditer(r"@Composable\s+fun\s+(\w+)", text):
    name = match.group(1)
    if any(k in name.lower() for k in ["book", "unit", "note", "subject", "screen", "detail"]):
        print(name)
