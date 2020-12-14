<?php

$errors = [];
$dayMap = array("Monday" => 1, "Tuesday" => 2, "Wednesday" => 3, "Thursday" => 4, "Friday" => 5, "Saturday" => 6, "Sunday" => 7,);

$workSheet = read_contents("data.json");

function read_contents($filename) {
    $raw_content = file_get_contents($filename);
    return json_decode($raw_content, TRUE);
}

function write_contents($filename, $contents) {
    file_put_contents($filename, json_encode($contents));
    return $contents;
}

if ($_POST) {
    $workDayName = $_POST['work_day_name'];
    $workFrom = $_POST['work_from'];
    $workTo = $_POST['work_to'];

    if (empty($workDayName)) {
        $errors[] = "work day is required";
    }

    if (empty($workFrom)) {
        $errors[] = "Hour from is required";
    } else if ($workFrom < 0 || $workFrom > 24) {
        $errors[] = "Hour from must be in range 0-24";
    }

    if (empty($workTo)) {
        $errors[] = "Hour to is required";
    } else if ($workTo < 0 || $workTo > 24) {
        $errors[] = "Hour to must be in range 0-24";
    }

    if (!empty($workFrom) && !empty($workTo)) {
        if ($workFrom >= $workTo) {
            $errors[] = "Work must be started before finished";
        }
    }

    if (count($errors) === 0) {
        $workSheet[] = array("day" => $dayMap[$workDayName], "from" => intval($workFrom), "to" => intval($workTo));
        write_contents("data.json", $workSheet);
    }

}

$aggregated = [];

foreach ($dayMap as $key => $day) {
    $aggregated[$day] = 0;
}

foreach ($workSheet as $work) {
    $day = $work['day'];
    $from = $work['from'];
    $to = $work['to'];
    $workingHour = $to - $from;
    $aggregated[$day] =$aggregated[$day]+ $workingHour;
}

$percentages=[];

foreach ($aggregated  as $key=> $value) {
    $percentages[$key]=$value/8.0*100;
}

?>

<?php if (count($errors) > 0): ?>
    <ul>
        <?php foreach ($errors as $error): ?>
            <li><?= $error ?></li>
        <?php endforeach ?>
    </ul>
<?php endif ?>


<style>th, td {
        width: 80px;
        height: 50px;
        border: solid gray 1px;
    }</style>
<table>
    <tr>
        <th>Monday</th>
        <th>Tuesday</th>
        <th>Wednesday</th>
        <th>Thursday</th>
        <th>Friday</th>
        <th>Saturday</th>
        <th>Sunday</th>
    </tr>
    <tr>
        <?php foreach ($percentages as $perc): ?>
            <td
                    style="background-color:
                    <?php if ($perc <= 100): ?>
                            hsl(120,50%,<?= $perc ?>%)
                     <?php else : ?>
                            red
                    <?php endif ?>
                            "> </td>
        <?php endforeach ?>

    </tr>
</table>
<form method="POST">
    <select name="work_day_name">
        <option>Monday</option>
        <option>Tuesday</option>
        <option>Wednesday</option>
        <option>Thursday</option>
        <option>Friday</option>
        <option>Saturday</option>
        <option>Sunday</option>
    </select>
    <input type="number" name="work_from">
    <input type="number" name="work_to">
    <button type="submit">Add</button>
</form>