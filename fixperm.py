import os, stat

# Ensure the .git dir itself is writable
try:
    os.chmod('.git', stat.S_IWRITE | stat.S_IREAD)
except Exception:
    pass

for root, dirs, files in os.walk('.git'):
    for n in files:
        path = os.path.join(root, n)
        try:
            os.chmod(path, stat.S_IWRITE | stat.S_IREAD)
        except Exception:
            pass
    for n in dirs:
        path = os.path.join(root, n)
        try:
            os.chmod(path, stat.S_IWRITE | stat.S_IREAD)
        except Exception:
            pass
