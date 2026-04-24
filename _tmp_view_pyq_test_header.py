from pathlib import Path
import sys

text = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/pyq/PyqScreen.kt").read_text(encoding="utf-8")
start = text.find("fun PyqTestScreen")
snippet = text[start:start+400]
sys.stdout.buffer.write(snippet.encode("utf-8", errors="replace"))
