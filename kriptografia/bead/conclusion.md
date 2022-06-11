## Tapasztalatok

A hashek elején és végén a keresés átlagosan ugyanannyi lépésben talált `n` karakternyi ütközést.
Megfelelő számú minta, vagy ugyanazon értékkészlet használata esetén a kis különbségek is eltűnnének.
A keresési idő eltérések a hash algoritmusok futásidejéből adódnak. Kivétel ahol bárhol keresünk, itt a feldolgozási 
idő miatt hosszabb a futásidő, a lépésszám pedig függ a hash hosszától.

A logaritmikus léptékű grafikonon lineáris növekedés látható `n` növekedésével, így a keresés nehézsége  
exponenciális.


#### Megjegyzés

A bármely pozíciós keresésnél sha3_512-re sikerült találni egy 12-es ismétlődést a 11-es keresésnél (5 keresésből ez nagy szerencse).  
Az eredménybe kétszer került be ugyanaz a string pár. Első gondolat a programhiba volt, de közelebbről megnézve  
a 12 karakter miatt 2 db 11 karakteresként ismerte fel a program.

```
{
    "hash_fn": "openssl_sha3_512",
    "char_count": 11,
    "type": "ALL",
    "round_count": 544811,
    "collided_fragment": "f7cf2d544c3",
    "str1": "31dd28a4-63a3-4f26-a224-8f05cfde6415",
    "str1_hash": "1126a01bae9ab6b5fe022aba1a85a785a3c965afc0e459e5b47373f9594e9b44b333812d73d2181dc5ef1e1bdeb*f7cf2d544c3*9d9a94407dd05c5c2517aaca52",
    "str2": "4bfbf426-7259-4df2-ae82-ab23cbc6ce59",
    "str2_hash": "6113475b0553f16fd65290f588f89fcc288af577bdc290bf784adb61ea1c2b676b5c2f1a473979c843da773c104*f7cf2d544c3*9716394b7808c2c1c541bf7191",
    "runtime": "51.048964738845825",
    "position": "91"
}{
    "hash_fn": "openssl_sha3_512",
    "char_count": 11,
    "type": "ALL",
    "round_count": 544811,
    "collided_fragment": "7cf2d544c39",
    "str1": "31dd28a4-63a3-4f26-a224-8f05cfde6415",
    "str1_hash": "1126a01bae9ab6b5fe022aba1a85a785a3c965afc0e459e5b47373f9594e9b44b333812d73d2181dc5ef1e1bdebf*7cf2d544c39*d9a94407dd05c5c2517aaca52",
    "str2": "4bfbf426-7259-4df2-ae82-ab23cbc6ce59",
    "str2_hash": "6113475b0553f16fd65290f588f89fcc288af577bdc290bf784adb61ea1c2b676b5c2f1a473979c843da773c104f*7cf2d544c39*716394b7808c2c1c541bf7191",
    "runtime": "51.048964738845825",
    "position": "92"
}

```
A teljes ismétlődés: `f7cf2d544c39`