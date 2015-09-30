
---

The Puffafilm - Motore di Raccomandazione Film
Sistemi Intelligenti Per Internet

---

Sommario:

- Requisiti di sistema

- Contenuto pacchetto

- Istruzioni per l'utilizzo

- Contatti



---

Requisiti di sistema

---

Java SE 1.6
1Gb Ram Libera



---

Contenuto pacchetto: puffafilm.zip

---

Il pacchetto contiene:

> - il jar del progetto: puffafilm-app-1.0.0.jar

> - lo zip contenente i javadoc: javadoc.zip

> - la cartella contenente i settaggi dei vari appender : logging

> - la cartella contenente il training set: dataset



---

Istruzioni per l'utilizzo

---


IMPORTANTE: Le cartelle dataset e logging devono essere presenti
sotto la stessa cartella che contiene il jar

Per l'utilizzo di puffafilm eseguire il jar con la seguente riga di
comando:

java  -server -XX:+UseConcMarkSweepGC -Xmx1024m -Xms512 -jar
puffafilm-app-1.0.0.jar nomeFileTest.dat nomeFileOutput.dat

Tali parametri sono necessari in quanto il jar contiene il codice
per il lancio e il caricamento dei dati nel db neo4j

Il file contenente i test deve essere formattato nel seguente modo
> - userID \t movieID \t timestamp
come definito dalle specifiche di progetto


AGGIUNGENDO COME ARGOMENTO interactive , L'APPLICAZIONE
CHIEDE ALL'UTENTE SE COMPIERE O MENO DETERMINATE AZIONI

- ETL DEI DATI : DEFAULT E NON INTERACTIVE ->TRUE

- WEB ADMIN INTERFACE : DEFAULT E NON INTERACTIVE ->TRUE

- RIMOZIONE DEL DB ALL'USCITA : DEFAULT E NON INTERACTIVE ->TRUE



---

Contatti

---

Fatica Federico - faticafederico@gmail.com
Fiungo Daniele - danielefiungo@gmail.com