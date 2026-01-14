✨ Hlavní myšlenka projektu

* Tento projekt slouží výhradně jako osobní tréninkový a learning projekt autora.
* Hlavním cílem není vytvoření komerčního produktu, ale:
  - systematické zlepšování backendových a frontendových dovedností
  - návrh modulární architektury
  - práce s databázemi, job schedulery a API
  - experimentování s AI integrací
* Aplikace je vyvíjena pro osobní potřeby autora a jako dlouhodobý sandbox pro testování technologií, návrhových rozhodnutí a nových přístupů.
____________________________________________


# TimeKeeper

TimeKeeper je osobní **home application** pro správu času, úkolů, financí a tréninku. Projekt vzniká primárně pro vlastní použití, s důrazem na **automatizaci**, **přehlednost** a postupnou integraci **AI asistence**.

Aplikace je rozdělena na **backend (Java)** a **frontend (TypeScript / React)** a je navržena modulárně tak, aby bylo možné jednotlivé části dále rozšiřovat.

---

## 🧩 Moduly aplikace

### 📂 Data (hlídání expirací)

* ukládání důležitých dokladů / položek
* evidence data platnosti
* automatický **Quartz job**
* e-mailové upozornění před expirací

---

### 💰 Transactions (finance)

* evidence:

  * příjmů
  * výdajů
  * měsíčních poplatků (opakované)
* přehled transakcí
* základní vizualizace (plánováno)

---

### ✅ Úkoly (To-Do List)

* správa úkolů
* termín dokončení
* označení hotovo / nehotovo
* napojení na existující **job pro upozornění**

---

### 🏋️ Tréninkový deník

* záznam tréninků
* rozdělení:

  * **anaerobní** (silový trénink)
  * **aerobní** (běh, kolo, plavání)
* evidence cviků, vah a opakování
* listování po týdnech

---

### 🤖 AI (plánováno / rozpracováno)

* automatická analýza tréninků
* generování doporučení
* týdenní AI report
* integrace přes LLM (Spring AI)

---

## 🏗️ Technologický stack

### Backend

* Java
* Spring Boot
* Spring Data / JPA / MyBatis (kombinace dle modulu)
* PostgreSQL
* Quartz Scheduler
* Flyway
* OpenAPI (REST API)

### Frontend

* TypeScript
* React
* plánovaná vizualizace dat (grafy)

---

## 🔐 Bezpečnost

* žádná citlivá data nejsou součástí repozitáře
* konfigurace přes **environment variables**
* secrets nejsou commitované

---

## 🚧 Stav projektu

* aktivně vyvíjen
* určeno primárně pro **osobní použití**
* architektura připravena na další rozšiřování

---

## 📄 Licence

Copyright (c) 2026 Tomáš Mikula

All rights reserved.

This source code is private and proprietary.
Unauthorized copying, modification, distribution,
or use of this software, via any medium, is strictly prohibited.

---

## 📌 Poznámka

Tento projekt slouží také jako **dlouhodobý learning project** (Java, architektura, AI integrace) a není zamýšlen jako hotový produkt.

---

> Pokud tě zajímá architektura, rozhodnutí v návrhu nebo konkrétní moduly, podívej se do zdrojového kódu nebo README jednotlivých částí.
