<?php

$renderElements = [];

$shapes = read_contents("svgdat.json");


foreach ($shapes as $shape) {
    $type = $shape['type'];
    if ($type == 'ellipse') {
        $cx = $shape['x'];
        $cy = $shape['y'];
        $rx = $shape['rx'];
        $ry = $shape['ry'];
        $renderElements[] = '<ellipse cx="' . $cx . '" cy="' . $cy . '" rx="' . $rx . '" ry="' . $ry . '" style="fill:lightblue;stroke:black;stroke-width:1" /> ';
    } else if ($type == 'circle') {
        $cx = $shape['x'];
        $cy = $shape['y'];
        $r = $shape['r'];
        $renderElements[] = '<circle cx="' . $cx . '" cy="' . $cy . '" r="' . $r . '" stroke="black" stroke-width="2" fill="red" />';
    } else if ($type == 'polyline') {
        $points = $shape['points'];
        if (count($points) >= 5) {
            $pointPairs = array_map('mapToStrPair', $points);
            $pointStr = implode(" ", $pointPairs);
            $renderElements[] = ' <polyline points="' . $pointStr . '" style="fill:none;stroke:black;stroke-width:1" />';
        }
    }

}

function mapToStrPair($coord) {
    return $coord['x'] . "," . $coord['y'];
}

function read_contents($filename) {
    $raw_content = file_get_contents($filename);
    return json_decode($raw_content, TRUE);
}

?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Webprogramming zh2_gyk php Zalan</title>
</head>
<body>
<?php if (count($renderElements) > 0): ?>
    <svg height="500" width="500">
        <?php foreach ($renderElements as $renderElement): ?>
            <?= $renderElement ?>
        <?php endforeach ?>
    </svg>
<?php endif ?>

</body>
</html>

