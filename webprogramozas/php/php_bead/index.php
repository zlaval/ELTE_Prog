<?php
session_start()

?>

<!DOCTYPE html>
<html lang="hu">

<head>
    <title>Set</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/css/all.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Permanent+Marker&family=Shadows+Into+Light&display=swap" rel="stylesheet">
</head>

<body>
<header>
    <?php include "./page/header.php" ?>
</header>
<main class="home-main">
    <div class="text-center">
        <b><span class="home-title">Köszöntjük a NemKoViD - Mondj nemet a koronavírusra! oldalán.</span></b>
    </div>
    <div class="text-center py-4">
        <img class="injection-image" src="img/injection.jpg">
    </div>

    <div class="row">
        <div class="col home-content">
            Elindult a regisztráció a nem koronavírus elleni védőoltásra.
            Az oldalon választhat a szabad időpontok közül.
            A regisztráció során a <b>nevet, lakcímet, taj-számot és e-mail-címet</b> kell megadni.
            A regisztráció célja az a kockázatok minimalizálása és az egy helyen tartózkodó emberek számának korlátozása.
            Kérjük vegye figyelembe, hogy a friss oltóanyag klinikai tesztje rövid idő alatt ment végbe, így előfordulhatnak mellékhatások.
            Ezek közütt szerepel a száj szárasság, víziszony, medvévé változás. Utóbbi esetben kérjük látogasson el a halpiacra vagy a szomszéd málnásába.
        </div>
        <div class="col home-quote">
            Amíg nem kerül sor az oltásra maradjanak otthon, és ha segítségre szorulnak, forduljanak az önkormányzathoz.
            Tudjuk, hogy a koronavírus nagyon gyorsan terjed, és ma már tudjuk, az idősekre a legveszélyesebb. Mindent tegyenek meg annak érdekében, hogy ne
            kelljen
            kimenniük az utcára, és ne kelljen elhagyniuk lakásukat. Ellátásukról az önkormányzatok kötelesek gondoskodni. Szükség esetén forduljanak a helyi
            önkormányzathoz.
            Ez a vírus mindannyiunk életére nagy hatással van. De ha betartjuk a fő szabályokat, elkerülhetjük a nagyobb bajt. Vigyázzunk egymásra!
        </div>
    </div>
    <div class="col-12 text-center">
        <button id="btn-fwd-booking" type="button" class="btn btn-warning"><i class="fa fa-arrow-right"></i>Irány a foglalás</button>
    </div>
</main>

<script src="/js/index.js"></script>
</body>
</html>