# pocibm
PCS Istituzionale – Adapter PMIS

Adapter per il reperimento dei dati dal sistema PMIS (Port Management Information System) delle Capitanerie di Porto.
  
Il Modulo fornisce l’integrazione al PMIS per il reperimento delle informazioni necessarie sia ai processi del PCS Istituzionale sia del PCS Operativo. 
Il modulo fornisce un sistema di schedulazione di invocazione dei servizi del PMIS con cache dei dati reperiti.
  
*  Java JDK 1.8.181
*  PostgreSQL 9.5.15

Di seguito sono descritte le caratteristiche minime necessarie per la predisposizione dell’ambiente di esecuzione del sistema PMIS Adapter.

| Nome | Caratteristiche HW | Descrizione | Sistema Operativo | vers. JAVA | Modulo SW & vers. |
|---|---|---|---|---|---|
| PMIS-ADAPTER-DB | RAM: 8192 Processori: 4 HDD: 80 GB | DBMS | RedHat Enterprise Linux Server v. 7.5 | N/A | PostgreSQL 9.5.15 |
| PMIS-ADAPTER-API | RAM: 8192 Processori: 4 HDD: 30 GB | J2EE Application Server | RedHat Enterprise Linux Server v. 7.5 | JDK 1.8u181 | Redhat JBoss EAP 7.1 |
| PMIS-ADAPTER-WRK | RAM: 16384 Processori: 8 HDD: 30 GB | Worker Business Logic | RedHat Enterprise Linux Server v. 7.5 | JDK 1.8u181 |   |


ulteriore pezzettino

