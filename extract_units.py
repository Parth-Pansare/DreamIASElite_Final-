text=open('app/src/main/java/com/app/dreamiaselite/ui/screen/screens/notes/NotesScreen.kt','r',errors='ignore').read() 
start=text.find('book.units.forEachIndexed')-120 
print(text[start:start+600])
