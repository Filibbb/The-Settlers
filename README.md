# gruppec-papp-projekt3-siedler-von-catan

# Beschreibung
Java basiertes Projekt von einer abgespeckten Version von die Siedler von Catan.

# Verfügbare Features
- Grundspiel
- Handel mit Bank 4:1
- Städte
- Räuber

# Gruppen-Mitglieder und Verantwortungsbereich
* Adrian Büchi - Teach Lead, Refactorings, Reviews
* Philippe Weber - HexBoard, Building, Commands
* Patric Fuchs - Thief, Dice throw, Commands

# Spiel starten
Starten kannst du die Siedler von Catan entweder durch starten von Application.java#main in einer Entwicklungsumgebung oder du lädts dir die kompilierte .jar-Datei hier oder durch die Release Section herunter und führst sie auf deinem System (Java muss natürlich installiert sein) mit dem Befehl java -jar Siedler_PAPP.jar aus.

# Klassendiagramm

https://phweber.ch/ZHAW/class-diagram-settlers.png

# Disclaimer

## Refactorings
Wir haben versucht den bestehenden Code möglichst so zu refactoren, dass er einigermassen verwendbar ist. Allerdings konnten wir bei weitem nicht alles des bestehenden Codes refactoren gerade `HexBoard.java` ist kaum angefasst worden aus zeitlichen Gründen.

Refactorings wurden hauptsächlich von Adrian Büchi übernommen, da er aufgrund seiner Erfahrung am ehesten dazu geeignet war. Die Refactorings wurden allerdings in der Gruppe behandelt und zusammen angeschaut.

# Teamrules

* Wir versuchen den Code in Englisch zu schreiben d.h. zum Beispiel ConsoleInputReader.java oder AddParagraph(String text, int paragraphNumber) anstelle von deutschen Namen.
* Wir arbeiten grundsätzlich mit Branches. Branches werden auf Englisch benammst und sollten beschreiben, was in diesem Branch gemacht wird.
* Wenn eine Änderung komplett ist, sollte diese im Idealfall in Review bei allen gestellt werden bevor auf den Master Branch gepushed wird. (4 Augen Prinzip)
* Git Commits bitte auf Englisch und nur Zustände comitten, die mindestens kompilieren und nach Möglichkeit nur auf die eigenen Feature Branches.

Wenn wir feststellen, dass etwas nicht funktioniert bitte frühzeitig melden, wenn die oben genannten Teamrules nur hinderlich sind dies ansprechen, dann werden die Neu definiert.
