Terminplaner

Ein kleines GUI-Programm, das es dem Nutzer ermöglicht, Termine einzutragen und zu 
speichern, und zu bearbeiten. Die Einträge werden separat in einer CSV-Datei in der 
Datei /src/data.txt gespeichert. Diese wird bei Programmstart ausgelesen und im GUI 
angezeigt. Beim Bearbeiten wird nicht nur der Eintrag im GUI editiert, sondern 
parallel wird auch die Textdatei überschrieben.

Persönliche Motivation:
- Erstellen eines sinnvollen GUIs zu Übungszwecken
- Arbeiten mit dem Prototyp einer Datenbank, auf der relevante Daten gespeichert 
sind und ausgelesen werden können

Funktionen:
- Neuen Eintrag erstellen: Per Klick auf "Neuer Eintrag" lässt sich ein neuer 
Datensatz erstellen
- Eintrag bearbeiten: Doppelklick auf eine Zeile ermöglicht die Bearbeitung
eines vorhandenen Eintrags
- Eintrag löschen: Den ausgewählten Eintrag aus dem GUI und der Datenbank
entfernen

Eine ausführbare .jar des Programms ist enthalten in /out/artifacts/Planer.jar

Herausforderungen:
Die größte Herausforderung war, die Datensätze in der Datenbank mit denen im GUI
synchron zu halten. Dies habe ich dadurch gelöst, dass zum einen bei Programmstart
die Datensätze der Datenbank ausgelesen und in nutzbare Objekte formatiert werden,
mit denen die Tabelle im GUI befüllt wird. Zum anderen habe ich für Erstellung
eines neuen Eintrags im GUI zugleich einen Zugang zur Datenbank gelegt, in der
der Datenbank der neue Eintrag im CSV-Format hinzugefügt wird. Entsprechender
Zugang wurde genutzt bei Änderung und Entfernen eines vorhandenen Eintrags.
