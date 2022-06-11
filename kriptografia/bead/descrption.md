##Kriptográfia beadandó

###Feladat

>Elemezz különböző hash algoritmusokat (pl. MD5, SHA[1-3], stb.) ütközések szempontjából. 
>Hány próbálkozásból sikerül és mennyi idő alatt található ütközés.
>
>- ha az első 1, 2, ..., 7 karakteren,
>- ha valahol a hashben 1, 2, ..., 7 karakteren,
>- ha a hash utolsó 1, 2, ..., 7 karakteren
>
>Keresünk ütközést. Ábrázoljuk az adatokat!
 
###Tervezés
A feladatban különböző hash algoritmusok által generált hash-ek hexadecimális formátumában keresünk ütközéseket
`n` egymást követő karakteren. A keresést a hash elején, végén illetve összes lehetséges pozícióján megismételjük.
Minden típusnál, minden `n` értékre 10 futás eredményének átlagát vesszük. Az algoritmus bemenete a hasítófüggvény,
egy `n` szám illetve egy leíró érték mely jelzi, mely pozición keresünk. A hash-elendő string értékeket az egyszerűség
kedvéért a uuid könyvtár segítségével generáljuk, ennek értéke egyedi lesz, illetve a maximális vizsgált `n` értéknél
hosszabb.

###Elemzés
A feladatban az `md5`,`sha1`,`sha3_512` hash algoritmusokat vizsgálom, de a kereső általánosítása miatt bármely algoritmus
átadható a függvénynek - amennyiben duck typing szerint ugyanaz mint a python hashlib-ben található funkciók.

A vizsgálat a hash hexadecimális értékeiből vesz `n` egymás követő értéket és ezek ütközését vizsgálja.
Egy érték 4 bitet reprezenál, lehetséges értékei `0` és `f` közöttiek.
A hash elején illetve végén így a lehetséges bitek számát b=(n*4) figyelembe véve ütközés található legfeljebb 
az összes permutáció (2<sup>b</sup>) + 1 eset után. (Születésnap támadás figyelmbe vételével ennek a gyöke).
Amennyiben az összes pozíciót bevesszük a keresésbe, **l=hash hossza** esetén `t=l-n-1` lehetséges pozición keresünk
ütközést. Ilyen esetben a fenti értéket még oszthatjuk a `t` értékkel.
Maximálisan ennyi lépésre lesz szükségünk hogy `n` hosszú ütközést találjunk.

###Kereső algoritmus
A kereső algoritmus egyszerű brute-force elven működik. Minden iterációban kér egy `uuid`-t, melyet az
adott algoritmussal elhashel, majd a megfelelő részt kivágja és megvizsgálja hogy megtalálható-e a szótárban.
Amennyiben megtalálható, az adott pozición ütközést találtunk és kiírjuk. Ha nem, elmenti a szótárban, mely kulcsa a
hash vizsgált szakasza, értéke pedig a string valamint a teljes hash value.

A kiírás formátuma:

```
Found 13 character(s) long collision at the BEGIN with <built-in function openssl_md5> fn.
Hash fragment: 047a21feccb6b, Iteration: 84925438, time: 0:04:30.384913
First string/hash: 5c75c767-a508-4350-9bd3-2404106affd2, 047a21feccb6b08ca758d66f37f44c88
Second string/hash: cb9ff2d9-a3b4-47c4-bef8-c6556e834a74, 047a21feccb6b03f700bddd18882d189
```

A vizsgálat legfeljebb `n=13` karakterig történt.
Mivel a kereső algoritmus a kiírás végett eltárolja a stringeket és hasheket, így
a memória használat magas. Ha csak a stringet tároljuk el, lehetőség van az n értékét
növelni kis mértékben. A teszteléshez használt gép 64Gb memóriával rendelkezett.

###Eredmények
Az eremények grafikus és szöveges formátumban a következő cellákban megtalálhatók.
A grafikonok mindhárom hash függvény statisztikáit tartalmazzák.
A szöveges formátum hossza miatt csak az md5 eredmények kerülnek bemutatásra.



