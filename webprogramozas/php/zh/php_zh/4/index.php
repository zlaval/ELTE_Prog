<?php
session_start();

function getValue($fieldName) {
    return $_POST[$fieldName];
}

if (empty($_SESSION['treasure'])) {
    $treasure = ["g" => 10, "s" => 0];
    $_SESSION['treasure'] = $treasure;
}

$treasure = $_SESSION['treasure'];

if ($_POST) {
    $g = getValue('gold');
    if (empty($g)) {
        $g = 0;
    }
    $s = getValue('silver');
    if (empty($s)) {
        $s = 0;
    }
    $g = intval($g);
    $s = intval($s);

    if ($s == 0 && $g == 0) {
        exit;
    }

    $type = getValue("type");

    $savedGold = $treasure['g'];
    $savedSilver = $treasure['s'];

    if ($type === 'spend') {
        if ($savedSilver < $s) {
            $savedSilver = $savedSilver + 12;
            $savedGold = $savedGold - 1;
        }
        if ($savedGold >= $s && $savedGold >= 0) {
            $savedGold = $savedGold - $g;
            $savedSilver = $savedSilver - $s;
            $newTreasure = ["g" => $savedGold, "s" => $savedSilver];
            $_SESSION['treasure'] = $newTreasure;
            echo "<tr><td>" . getInitDate() . "</td> <td>" . $savedGold . "g " . $savedSilver . "s " . "</td> </tr>";
        }

        exit;
    } else {
        $savedSilver = $savedSilver + $s;
        if ($savedSilver >= 12) {
            $savedGold = $savedGold + 1;
            $savedSilver = $savedSilver - 12;
        }
        $savedGold = $savedGold + $g;
        $newTreasure = ["g" => $savedGold, "s" => $savedSilver];
        session_unset();
        $_SESSION['treasure'] = $newTreasure;
        echo "<tr><td>" . getInitDate() . "</td> <td>" . $savedGold . "g " . $savedSilver . "s " . "</td> </tr>";
        exit;
    }
}

function getInitialTreasure() {
    return $_SESSION['treasure']['g'] . "g " . $_SESSION['treasure']['s'] . "s";
}

function getInitDate() {
    $date = new DateTime();
    return $date->format('Y.m.d H:i:s');
}

?>


<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>4. feladat</title>
</head>
<body>
<h1>4. feladat</h1>

<div>
    <h2>Egyenleg: </h2>

</div>


<h2>Új tranzakció</h2>
Arany: <input type="number" id="gold" name="gold" min="0" max="99" step="1" value="0"><br>
Ezüst: <input type="number" id="silver" name="silver" min="0" max="11" step="1" value="0"><br>
<button id="income">Bevétel</button>
<button id="spend">Kiadás</button>

<h2>Tranzakciós napló</h2>
<table id="transactions">
    <tr>
        <th>Időpont</th>
        <th>Egyenleg</th>
    </tr>
    <tr>
        <td> <?= getInitDate() ?></td>
        <td> <?= getInitialTreasure() ?> </td>
    </tr>
</table>

<script>
    const incomeBtn = document.querySelector("#income");
    const spendBtn = document.querySelector("#spend");
    const inpGold = document.querySelector("#gold");
    const inpSilver = document.querySelector("#silver");
    const table = document.querySelector("#transactions")

    incomeBtn.addEventListener('click', event => {
        event.preventDefault()
        postToServer("income")
    })
    spendBtn.addEventListener('click', event => {
        event.preventDefault()
        postToServer("spend")
    })

    async function postToServer(eventType) {
        const formData = new URLSearchParams()
        formData.append("type", eventType)
        formData.append("gold", inpGold.value)
        formData.append("silver", inpSilver.value)

        const response = await fetch('index.php', {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST',
            body: formData
        })

        const result = await response.text()
        if (result) {
            table.innerHTML += result;
        }
        console.log(result)
    }

</script>

</body>
</html>