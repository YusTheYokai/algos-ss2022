# algos-ss2022

- [algos-ss2022](#algos-ss2022)
  - [Hashtabelle](#hashtabelle)
    - [Beschreibung der Datenstruktur und Hashfunktion](#beschreibung-der-datenstruktur-und-hashfunktion)
      - [**Datenstruktur**](#datenstruktur)
      - [**Hashfunktion**](#hashfunktion)
    - [Aufwandsabschätzung](#aufwandsabschätzung)
      - [**Allgemein nach Big O notation**](#allgemein-nach-big-o-notation)
      - [**bei 1000 Einträgen**](#bei-1000-einträgen)
  - [Treecheck](#treecheck)
    - [Beschreibung der rekursiven Funktionen](#beschreibung-der-rekursiven-funktionen)
    - [Aufwandsabschätzung](#aufwandsabschätzung-1)
  - [Shortest Path](#shortest-path)
    - [Output Format](#output-format)
    - [Aufwandsabschätzung](#aufwandsabschätzung-2)
      - [Datei einlesen](#datei-einlesen)
      - [Graph erstellen](#graph-erstellen)
      - [Dijkstra](#dijkstra)
    - [Laufzeit](#laufzeit)

## Hashtabelle
### Beschreibung der Datenstruktur und Hashfunktion
#### **Datenstruktur**
Eine `Hashtable<K, V>`, `K` bestimmt den Datentyp der Keys, `V` bestimmt den Datentyp der Values, bekommt die Größe im Konstruktor übergeben. Diese wird zum Erstellen eines Arrays vom Datentyp `KeyValue<K, V>` verwendet.

Ein `KeyValue<K, V>`-Paar besteht aus einem Key, einem Value und einer Flag `deleted`, welche besagt, ob an dieser Stelle ein Eintrag existierte, sodass bei der quadratischen Sondierung gelöschte Einträge betrachtet werden können.

Die Kursdaten werden in einer Liste in der Klasse `Stock` gespeichert. Die Begrenzung für die 30 neusten Kurseinträge erfolgt beim Importieren.

#### **Hashfunktion**

Da die Hashtabelle anhand Generics generisch gestaltet ist wird Javas eigene Hashfunktion verwendet, jede Klasse besitzt diese durch die Vererbung von `Object`. In der Aktien-Anwendung werden außschließlich `String`s als Keys verwendet, die Hashfunktion hierfür sieht wie folgt aus:

![Java String Hash](https://raw.githubusercontent.com/YusTheYokai/algos-ss2022/f4c57671622945e60e405454546905b243cd5869/src/main/resources/javaStringHash.png)

### Aufwandsabschätzung
#### **Allgemein nach Big O notation**

| Aktion   | Hashtable | Array  | LinkedList  |
|----------|-----------|--------|-------------|
| Einfügen | O(1)      | O(n)   | O(1)        |
| Suchen   | O(1)      | O(n)   | O(n)        |
| Löschen  | O(1)      | O(n)   | O(1)        |

#### **bei 1000 Einträgen**

Aufwand für Sondierung: `1/(1-alpha)`. Bei einer Größe von 2003 ergibt die Rechnung `1/(1-(1000/2003))` ungefähr 2.

| Aktion   | Hashtable |
|----------|-----------|
| Einfügen | $O(2)$    |
| Suchen   | $O(2)$    |
| Löschen  | $O(2)$    |

## Treecheck
### Beschreibung der rekursiven Funktionen
Es gibt folgende rekursive Methoden:

- Node
  - height
- Tree
  - addTraverse
  - isAvlTraverse
  - minTraverse 
  - maxTraverse
  - sumTraverse
  - countTraverse
  - search
  - contains
  - containsTraverse

Generell arbeiten alle Methoden anhand des gleichen Prinzips, die Methoden werden für die linke und für die rechte Node rekursiv aufgerufen.

### Aufwandsabschätzung
Anhand dessen, dass die Methoden für Links und für Rechts rekursiv aufgerufen werden handelt es sich um $O(log(n))$.

## Shortest Path
### Output Format
Anhand des Beispieles Floridsdorf nach Roßauer Lände:  
```
00 |  U6 | Floridsdorf  
02 |  U6 | Neue Donau  
04 |  U6 | Handelskai  
05 |  U6 | Dresdner Strasse  
07 |  U6 | Jaegerstrasse  
08 |  U6 | Spittelau  
+5 | --- | Umstieg von U6 zu U4  
13 |  U4 | Spittelau  
14 |  U4 | Friedensbruecke  
15 |  U4 | Rossauerlaende
```  

### Aufwandsabschätzung
#### Datei einlesen
Es wird über jede Linie, sowie jede Einstationenfahrt iteriert. Dadurch ergibt sich $O(n*m)$, wo $m$ der Anzahl an Einstationenfahrten entspricht. Dadurch, dass nicht gewährleistet ist, dass jede Linie gleich viele Stationen besitzt, rechnen wir auf $O(n)$ runter.

#### Graph erstellen
Beim Zusammenbauen des Graphens wird über die Commutes (=Einstationenfahrten) iteriert, dementsprechend liegt der Aufwand bei $O(n)$, wenn $n$ der Anzahl von Commutes entspricht.

#### Dijkstra
Der Dijkstra-Algorithmus hat einen Aufwand von $O(S \log N)$, wobei $S$ der Anzahl der Stationen, und $N$ der Anzahl aller Nachbarn entspricht.

### Laufzeit
| Datei |von        |nach        |Vertices $S$|Edges $N$|Zeit|$S \log N$|
|-------|-----------|------------|------------|---------|----|----------|
|6_60_62|Rodaun     |Valiergasse |77          |160      |1ms |390.788384|
|  Half |Praterstern|Schottenring|366         |1134     |4ms |2574.26337|
|  Full |Leopoldau  |Simmering   |671         |2476     |10ms|5243.46215|
