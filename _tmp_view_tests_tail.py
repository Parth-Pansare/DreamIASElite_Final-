from pathlib import Path

path = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/tests/TestsScreen.kt")
lines = path.read_text().splitlines()
target = "TestSubjectScreen"
for idx, line in enumerate(lines, 1):
    if target in line:
        for j in range(idx-5, idx+40):
            if 1 <= j <= len(lines):
                print(f"{j}: {lines[j-1]}")
        break
