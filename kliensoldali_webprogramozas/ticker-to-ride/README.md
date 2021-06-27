## Tóth Zalán

## cz72ym

### Kliensoldali webprogramozás 2021 tavaszi félév.

Ezt a megoldást **<Tóth Zalán, cz72ym>** küldte be és készítette a **Kliensoldali webprogramozás kurzus** __*Ticket to
ride*__ feladatához. Kijelentem, hogy ez a megoldás a saját munkám. Nem másoltam vagy használtam harmadik féltől
származó megoldásokat. Nem továbbítottam megoldást hallgatótársaimnak, és nem is tettem közzé. Az Eötvös Loránd
Tudományegyetem Hallgatói Követelményrendszere (ELTE szervezeti és működési szabályzata, II. Kötet, 74/C. §) kimondja,
hogy mindaddig, amíg egy hallgató egy másik hallgató munkáját - vagy legalábbis annak jelentős részét - saját
munkájaként mutatja be, az fegyelmi vétségnek számít. A fegyelmi vétség legsúlyosabb következménye a hallgató
elbocsátása az egyetemről.

## 1 felvonás használata:

- Ideiglenes gombokkal bejárhatóak a képernyők
- Kocsik paklira kattintva húzható kártya (jelenleg csak animáció)
- Cél kártya hover eseménye megjeleníti az útvonalat a két város között
- Városra kattintva kijelölődik a város és megjelölődnek a szomszédos városok
- Kijelölt állpotban a megjelölt szomszédos városra egy minta építés zajlik (kijelölés visszavonására eltűnik)
- A kijelölések újbóli kattintással ugyanarra az elemre eltüntethetők
- Hosszabb út kis hackel ideiglenesen építhető, miután építettünk a fenti módszerrel két város között, egy távolabbi
  pontra kattintva a kijelölt város szerepét átveszi a távoli város és a 'hova' város marad az eredeti így a két város
  között egy szimulált építés lezajlik

## 2 felvonás használata:

- feladat kiírás szerint
- statek a states mappában
- statek betöltése a board képernyőre lépve

### Megoldott részfeladatok

- [x] Redux használata (1pt)
- [x] Legalább 2 action (1pt)
- [ ] Minden actionhöz action creator (1pt)
- [x] A root reducer legalább két alreducerre van bontva (1pt)
- [x] Jól szervezett könyvtárstruktúra (1pt)
- [ ] Redux-dev-tool-lal oda-vissza léptethető állapottér (1pt)
- [x] Redux-dev-tool-ba betölthető előre elkészített állapotterek és lépések (1pt)
- [x] Játékoldal: a játékoldalra érve a játéktábla 2 játékossal elő van készítve (2pt)
- [x] Játékoldal: aktív játékos jelölése (1pt)
- [x] Játékoldal: az aktív játékosnál a megfelelő adatok jelennek meg (célok, kártyák, vagonok) (2pt)
- [x] Játékoldal: az aktív játékosnál az utolsó két művelete feltüntetésre kerül (1pt)
- [x] Játékoldal: teljesítetlen cél fölé víve az egeret a két végpont megjelölésre kerül a térképen (1pt)
- [x] Játékoldal: teljesített cél fölé víve az egeret a két végpont megjelölésre kerül a térképen és az útvonal is
  kiemelt lesz (2pt)
- [x] Játékoldal: ha most került ránk a sor, tudunk az asztalról egy vasútkocsi-kártyalapot húzni (1pt)
- [x] Játékoldal: ha most került ránk a sor, tudunk a pakliból egy vasútkocsi-kártyalapot húzni (1pt)
- [x] Játékoldal: tudunk még egy kártyalapot húzni (1pt)
- [x] Játékoldal: ha mozdonyt húztunk a felfedett lapok közül, akkor nem tudunk még egy kártyalapot húzni (2pt)
- [x] Játékoldal: a húzott lapok megjelennek a kezünkben (1pt)
- [ ] Játékoldal: a húzás során a kártyák animálva kerülnek a kezünkbe vagy a játékos adataihoz (+1pt)
- [x] Játékoldal: húzás közben nem lehet építeni vagy célkártyát húzni (1pt)
- [x] Játékoldal: húzás befejeztével a másik játékos jön (1pt)
- [x] Játékoldal: ha most került ránk a sor, akkor elkezdhetünk utat építeni (1pt)
- [x] Játékoldal: építkezésnél csak szomszédos városok között tudunk utat építeni (1pt)
- [x] Játékoldal: építkezésnél csak akkor tudunk utat építeni, ha van hozzá kártya a kezünkben (1pt)
- [x] Játékoldal: építkezésnél a szükséges kártyákat akár többféle kombinációból összeállíthatjuk, mozdonyok
  segítségével (2pt)
- [x] Játékoldal: építkezés végén az út megépítésre kerül, a kártyák a kezünkből a dobópakliba kerülnek, a vagonok száma
  csökken (1pt)
- [ ] Játékoldal: a vagonok animálva kerülnek a táblára (+1pt)
- [x] Játékoldal: építkezés közben nem lehet vasútkocsi-kártyát vagy célkártyát húzni (1pt)
- [x] Játékoldal: az építkezés befejeztével a másik játékos jön (1pt)
- [ ] Játékoldal: ha most került ránk a sor, akkor a célkártyapaklira kattintva 3 új célkártyát kapunk (+1pt)
- [ ] Játékoldal: egy célkártyára kattintva megjelölhető (+1pt)
- [ ] Játékoldal: megjelölt célkártyára kattintva a jelölés visszavonódik (+1pt)
- [ ] Játékoldal: egy gomb akkor elérhető, ha legalább 1 célkártya ki van választva (+1pt)
- [ ] Játékoldal: erre a gombra kattintva a kijelölt kártyák átkerülnek a céljaink közé, a többi a célkártya pakli
  aljára (+1pt)
- [ ] Játékoldal: célkártyahúzás közben nem lehet vasútkocsi-kártyát húzni vagy építkezni (+0,5pt)
- [ ] Játékoldal: célkártyák animálva kerülnek a kezünkbe vagy a játékos adataihoz (+1pt)
- [ ] Játékoldal: a célkártyahúzás befejeztével a másik játékos jön (+0,5pt)
- [ ] Játékoldal: ha valakinek kettő vagy kevesebb vagonja van, akkor még egy teljes kör mehet (+1pt)
- [x] Játékoldal: a játék végén megjelenik az összegző táblázat (1pt)
- [x] Játékoldal: összegző táblázatban az utak pontszáma (1pt)
- [x] Játékoldal: összegző táblázatban a célok pontszáma (1pt)
- [x] Játékoldal: összegző táblázatban a célok listája zöld/piros (2pt)
- [ ] Játékoldal: összegző táblázatban a célok listájában fölé víve az egeret megjelenik az útvonal (2pt)
- [ ] Játékoldal: összegző táblázatban a leghosszabb út (1pt)

## 3 felvonás használata:

- feladat kiírás szerint

### Megoldott részfeladatok

- [x] Az 1. játékos a főoldalon megadja a nevét, beállítja a játékosok számát 2-re és új játékszobát indít. Ekkor
  bekerül a várakozó szobába (itt jelenik meg a szoba kódja, amit el tud küldeni a játékon kívül a 2. játékosnak). (2pt)
- [x] A 2. játékos a főoldalon beírja a kódot, és így csatlakozik egy meglévő szobához. Mivel a szoba így megtelt, a
  folyamat végén mindkét játékos a játékoldalra kerül. (3pt)
- [x] A főoldalon a szobához csatlakozásnál ha érvénytelen a kód, akkor nem tud továbblépni. (Érvénytelen kód: nem
  létező szobaazonosító, vagy olyan szoba, amelyben már megtelt, vagy lezárt játék van. A kód érvényességét a szerver
  ellenőrzi.) (2pt)
- [x] A játékoldalon megjelenik a játéktábla. Mindkét játékos ugyanazokat az adatokat látja (térkép, játékosok, kirakott
  lapok, stb), kivéve azt, hogy mindegyik játékos a saját kezét látja. (3pt)
- [x] Az aktív játékos lépéséről a 2. játékos is értesül (változnak a kirakott lapok, megjelenik egy épített út a
  térképen, módosul a játékos history) (3pt)
- [ ] Az aktív játékos lépéséről a 2. játékos animációval értesül (+2pt)
- [x] Amíg az 1. játékos aktív, addig a 2. játékos csak "nézegetni" tud, kezében a lapokat, a céljait, de lépni nem
  tud. (3pt)
- [x] Az 1. játékos végeztével a 2. játékos lesz az aktív, és minden igaz rá, ami fentebb az 1. játékosra, csak
  fordítva. (2pt)
- [x] Játék végén egy gombra kattintva visszakerülnek a főoldalra. (2pt)
- [x] A várakozó szobába kerülnek a játékosok, amíg össze nem gyűlik az elején beállított játékosszám. (2pt)
- [x] A várakozó szobában feltüntetésre kerül a kapcsolódott játékosok neve. (+3pt)
- [x] A játékoldalon a játéktér állapota szinkronizálva van az összes játékossal. (2pt)
- [x] Csak az aktív játékos léphet. (2pt)
- [x] A nem aktív játékosok csak "nézelődhetnek" (kéz, célok, stb) (2pt)
- [x] Az aktív játékos lépésének befejeztével ciklikusan a következő játékos jön. (2pt)