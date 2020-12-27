<?php

function read_contents($filename) {
    $raw_content = file_get_contents($filename);
    return json_decode($raw_content, TRUE);
}

function write_contents($filename, $contents) {
    file_put_contents($filename, json_encode($contents));
    return $contents;
}

function getValue($fieldName) {
    return $_POST[$fieldName];
}

$treasures = read_contents('save.json');
if ($_POST) {
    if (isset($_POST["delete"])) {
        unset($treasures[$_POST["delete"]]);
        write_contents("save.json", $treasures);
    } else {
        $name = getValue("name");
        $value = intval(getValue("value"));
        $color = getValue("color");
        $keep = getValue("keep") === 'igen';

        $treasures[$name] = array("name" => $name, "value" => $value, "color" => $color, "keep" => $keep);
        write_contents("save.json", $treasures);
    }

}
?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>3. feladat</title>
</head>
<body>
<h1>3. feladat</h1>

<h2>Űrlap</h2>
<form method="POST">
    <label>Kincs neve:</label>
    <input type="text" name="name"></br>
    <label>Kincs értéke:</label>
    <input type="number" name="value"></br>
    <label>Kincs színe:</label>
    <select name="color" id="color">
        <option></option>
        <option value="piros">piros</option>
        <option value="narancs">narancs</option>
        <option value="sárga">sárga</option>
        <option value="zöld">zöld</option>
        <option value="kék">kék</option>
        <option value="lila">lila</option>
    </select></br>
    <label>Megtartjuk:</label></br>
    <input type="radio" name="keep" value="igen">igen<br>
    <input type="radio" name="keep" value="nem">nem<br>
    <button type="submit">Raktárba</button>
</form>

<h2>Kincslista</h2>

<table>
    <tr>
        <th>Név</th>
        <th>Érték</th>
        <th>Szín</th>
        <th>Megtartjuk</th>
        <th></th>
    </tr>
    <?php foreach ($treasures as $treasure): ?>
        <tr>
            <td> <?= $treasure['name'] ?></td>
            <td><?= $treasure['value'] ?></td>
            <td><?= $treasure['color'] ?></td>
            <?php if ($treasure['keep']): ?>
                <td>Megtartjuk</td>
            <?php else: ?>
                <td>Eladományozzuk</td>
            <?php endif ?>

            <td>
                <form method="post">
                    <input type="hidden" name="delete" value="<?= $treasure['name'] ?>">
                    <button type="submit">Törlés</button>
                </form>
            </td>

        </tr>
    <?php endforeach ?>
</table>


</body>
</html>