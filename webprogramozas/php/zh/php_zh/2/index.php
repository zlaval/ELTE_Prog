<?php

$errors=[];

if($_GET){
    $goblins=getValue("goblins");
    $goblins=getValue("chief");
    $goblins=getValue("shovels");

/*
 * a. (2 pont) Amennyiben a goblins paraméter nem létezik, nem szám, nem egész, vagy nem pozitív: Érvénytelen goblin mennyiség!
b. (1,5 pont) Amennyiben a chief paraméter nem létezik, vagy nincs benne legalább egy szóköz: Érvénytelen vezető!
c. (2 pont) Amennyiben a vezető rangja nem szerepel a goblin rangok közt: Érvénytelen rang!, ha pedig nem éri el a nagyfőnök szintet: Túl alacsony rang!
A goblin rangok (legkisebbtől legnagyobbig):
goblinka, kisfőnök, nagyfőnök, főfőnök, törzsfő
d. (1,5 pont) Amennyiben a shovels paraméter nem létezik, nem szám, nem egész, vagy negatív: Érvénytelen ásó mennyiség!, ha pedig kisebb, mint a goblinok száma: Túl kevés ásó! (ez utóbbit elég akkor megnézni, ha az ásók száma érvényes volt)
e. (1 pont) Ha minden paraméter megfelelő: Indulhat az akció!, ha pedig legalább kétszer annyi ásó van, mint goblin: Gyorsan megszerezzük a kincset!
 */
}

function getValue($name){
    return $_GET[$name];
}


?>



<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>2. feladat</title>
</head>
<body>
  <h1>2. feladat</h1>

  <h2>Üzenetek</h2>
  <?php if (count($errors) > 0): ?>
      <ul>
          <?php foreach ($errors as $error): ?>
              <li><?= $error ?></li>
          <?php endforeach ?>
      </ul>
  <?php endif ?>

  <h2>Próbalinkek</h2>
  <a href="index.php?goblins=5&chief=Snuch Nawdow nagyfőnök&shovels=7"><pre>index.php?goblins=5&chief=Snuch Nawdow nagyfőnök&shovels=7</pre></a>
  <a href="index.php?goblins=5&chief=Snuch Nawdow nagyfőnök&shovels=10"><pre>index.php?goblins=5&chief=Snuch Nawdow nagyfőnök&shovels=10</pre></a>
  <a href="index.php"><pre>index.php</pre></a>
  <a href="index.php?goblins=nemszám&chief=nincsszóköz&shovels=nemszám"><pre>index.php?goblins=nemszám&chief=nincsszóköz&shovels=nemszám</pre></a>
  <a href="index.php?goblins=-5&chief=Snuch Nawdow nagyfőnök&shovels=10"><pre>index.php?goblins=-5&chief=Snuch Nawdow nagyfőnök&shovels=10</pre></a>
  <a href="index.php?goblins=16.2&chief=Snuch Nawdow nagyfőnök&shovels=10"><pre>index.php?goblins=16.2&chief=Snuch Nawdow nagyfőnök&shovels=10</pre></a>
  <a href="index.php?goblins=16&chief=Snuch Nawdow nagyfőnök&shovels=10"><pre>index.php?goblins=16&chief=Snuch Nawdow nagyfőnök&shovels=10</pre></a>
  <a href="index.php?goblins=5&chief=Snuch Nawdow párttitkár&shovels=10"><pre>index.php?goblins=5&chief=Snuch Nawdow párttitkár&shovels=10</pre></a>
  <a href="index.php?goblins=5&chief=Snuch Nawdow kisfőnök&shovels=10"><pre>index.php?goblins=5&chief=Snuch Nawdow kisfőnök&shovels=10</pre></a>
</body>
</html>