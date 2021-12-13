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
Starten kannst du die Siedler von Catan durch starten von Application.java#main in einer Entwicklungsumgebung.
Um das Projekt zu kompilieren kannst du javac benutzen: javac Application.java
Danach kann das Projekt mit java Application.class gestartet werden

# Klassendiagramm
Das Klassendiagram ist hier im Wiki zu finden: 
https://github.zhaw.ch/PM1-IT21taWIN-kars-pero-tebe/gruppec-papp-projekt3-siedler-von-catan/wiki

# Java-Docs
Die Dokumentation ist hier zu finden:
https://phweber.ch/ZHAW/the-settlers/JavaDocs/

# Tests
Die Unit-Tests wurden in JUnit-5 geschrieben und entsprechend möglichst prägnant benannt und mit einem kurzen Satz beschrieben was der Test genau testet. Die Tests wurden aufgeteilt pro Klasse für die sie die Logik testeten (siehe Testordner Struktur und Klassennamen). Nach Möglichkeit testen die Tests sowohl positive Testfälle als auch negative Testfälle. D.h. es wird auch getestet, was passiert bei "fehlerhaften" Inputs oder Verwendungen von Logik. Der Test ist dann entsprechend benannt oder es ist vermerkt in den J-Docs. Wir versuchten mit unseren Tests, über wohldefinierte Schnittstellen möglichst sowohl die essentielle Spiellogik, als auch die Hilfsmethoden und das Board zu testen.

**Alle Tests wurden am 12.12.2021 um 00:50 ausgeführt und liefen erfolgreich durch. **

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
