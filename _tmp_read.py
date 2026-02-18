from itertools import islice

path = r"app/src/main/java/com/app/dreamiaselite/ui/screen/screens/notes/NotesScreen.kt"
start = 200
end = 400

with open(path, encoding="utf-8") as fh:
    data = ''.join(islice(fh, start, end))

import sys
sys.stdout.buffer.write(data.encode("utf-8", errors="replace"))
