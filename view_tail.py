import pathlib 
p=pathlib.Path('app/src/main/java/com/app/dreamiaselite/ui/screen/screens/notes/NotesScreen.kt') 
data=p.read_bytes() 
print(data[-12000:].decode('utf-8','ignore'))
