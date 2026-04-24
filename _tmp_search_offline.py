import pathlib, re, sys

root = pathlib.Path("app/src/main/java")
for path in root.rglob("*.kt"):
    text = path.read_text(encoding="utf-8", errors="ignore")
    if re.search(r"(?i)offline|download", text):
        sys.stdout.write(str(path).replace("\\\\", "/") + "\\n")
