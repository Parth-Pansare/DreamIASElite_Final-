from pathlib import Path

path = Path("app/src/main/java/com/app/dreamiaselite/ui/screen/screens/tests/TestSubjectScreen.kt")
lines = path.read_text().splitlines()
for idx, line in enumerate(lines, 1):
    if "test_session/" in line:
        for j in range(idx-3, idx+3):
            if 1 <= j <= len(lines):
                print(f"{j}: {lines[j-1]}")
        print("---")
