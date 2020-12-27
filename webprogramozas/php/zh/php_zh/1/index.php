<?php
$activities = [
    1 => ["name" => "alvás", "difficulty" => 0.05],
    2 => ["name" => "bányászás", "difficulty" => 0.6],
    3 => ["name" => "család", "difficulty" => 0.4],
    4 => ["name" => "programozás", "difficulty" => 0.95],
    5 => ["name" => "zsákmányolás", "difficulty" => 0.7],
    6 => ["name" => "vadászat", "difficulty" => 0.6],
    7 => ["name" => "játék", "difficulty" => 0.0],
    8 => ["name" => "főzés", "difficulty" => 0.6]];
$goblins = [
        "WEB'LIN" =>
            [
                ["startHour" => 0, "activityKey" => 3],
                ["startHour" => 1, "activityKey" => 3],
                ["startHour" => 3, "activityKey" => 5],
                ["startHour" => 4, "activityKey" => 4],
                ["startHour" => 5, "activityKey" => 4],
                ["startHour" => 7, "activityKey" => 1]
            ],
    "HUN'TER" =>
        [
                ["startHour" => 0, "activityKey" => 1],
                ["startHour" => 1, "activityKey" => 6],
                ["startHour" => 3, "activityKey" => 3],
                ["startHour" => 4, "activityKey" => 3],
                ["startHour" => 5, "activityKey" => 6],
                ["startHour" => 7, "activityKey" => 6]
        ],
    "MOT'HER" =>
        [
                ["startHour" => 0, "activityKey" => 3],
                ["startHour" => 1, "activityKey" => 3],
                ["startHour" => 3, "activityKey" => 6],
                ["startHour" => 4, "activityKey" => 8],
                ["startHour" => 5, "activityKey" => 8],
                ["startHour" => 7, "activityKey" => 3]
        ],
    "GOB'KID" =>
        [
                ["startHour" => 0, "activityKey" => 7],
                ["startHour" => 1, "activityKey" => 7],
                ["startHour" => 3, "activityKey" => 7],
                ["startHour" => 4, "activityKey" => 7],
                ["startHour" => 5, "activityKey" => 7],
                ["startHour" => 7, "activityKey" => 7]]
        ];

$data = [];

foreach ($goblins as $name => $goblinActivities) {
    foreach ($goblinActivities as $activity) {
        $data[$activity['startHour']] = $activity['startHour'];
    }
}

function getActivity($hour, $name) {
    global $goblins;
    global $activities;
    $activityArray = $goblins[$name];


    foreach ($activityArray as $act) {
        if ($act["startHour"] === intval($hour)) {
            return ($activities[$act["activityKey"]]["name"]);
        }
    }

    return "";
}


function getColor($hour, $name) {
    global $goblins;
    global $activities;
    $activityArray = $goblins[$name];


    foreach ($activityArray as $act) {
        if ($act["startHour"] === intval($hour)) {
            $diff= ($activities[$act["activityKey"]]["difficulty"]);
            if($diff<=0.5){
                return 'style="background-color:lightgreen;"';
            }else if($diff<0.8){
                return 'style="background-color:orange;"';
            }else{
                return 'style="background-color:red;"';
            }
        }
    }

    return "";
}

?>

<!DOCTYPE html>
<html lang="hu">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>1. feladat</title>
    <style>
        table, td, th {
            border: 1px black solid;
            border-collapse: collapse;
        }

        td {
            text-align: center;
        }
    </style>
</head>

<body>
<h1>1. feladat</h1>
<h2>Időbeosztás</h2>
<table>
    <tr>
        <th>Óra</th>
        <?php foreach ($goblins as $name => $activity): ?>
            <th>
                <?= $name ?>
            </th>
        <?php endforeach ?>
    </tr>
    <?php for ($i = 0; $i <= 7; $i++): ?>
        <tr>
            <td><?= $i ?></td>
            <?php foreach ($goblins as $name => $activity): ?>
                <td  <?= getColor($i, $name) ?>  ><?= getActivity($i, $name) ?></td>
            <?php endforeach ?>

        </tr>

    <?php endfor ?>
</table>
</body>

</html>