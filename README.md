# algos-ss2022

1. [algos-ss2022](#algos-ss2022)
   1. [Hashtabelle](#hashtabelle)
      1. [Beschreibung der Datenstruktur und Hashfunktion](#beschreibung-der-datenstruktur-und-hashfunktion)
         1. [**Datenstruktur**](#datenstruktur)
         2. [**Hashfunktion**](#hashfunktion)
      2. [Aufwandsabschätzung](#aufwandsabschätzung)
         1. [Allgemein nach Big O notation](#allgemein-nach-big-o-notation)
         2. [bei 1000 Einträgen](#bei-1000-einträgen)
   2. [Treecheck](#treecheck)
      1. [Beschreibung der rekursiven Funktionen](#beschreibung-der-rekursiven-funktionen)
      2. [Aufwandsabschätzung](#aufwandsabschätzung-1)

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
