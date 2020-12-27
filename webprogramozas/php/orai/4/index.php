<?php
// $contents = file_get_contents(__DIR__ . '/data.dat');
// $lines = explode("\n", $contents);

// var_dump($lines);

// $filename = __DIR__ . '/data.json';
// $raw_contents = file_get_contents($filename);
// $contents = json_decode($raw_contents, TRUE);

// var_dump($contents);

// $contents[] = 'pineapple';
// $encoded_contents = json_encode($contents);

// file_put_contents($filename, $encoded_contents);

function read_contents($filename) {
    $raw_content = file_get_contents($filename);

    return json_decode($raw_content, TRUE);
}

// var_dump(read_contents('data.json'));

function write_contents($filename, $contents) {
    file_put_contents($filename, json_encode($contents));
}

function insert($filename, $element) {
    $contents = read_contents($filename);
    $contents[] = $element;

    write_contents($filename, $contents);
}

// var_dump(insert('data.json', [
//     'name' => 'pear',
//     'count' => 2
// ]));

function read_filtered_contents($filename, $predicate) {
    return array_filter(read_contents($filename), $predicate);
}

// var_dump(read_filtered_contents('data.json', function ($fruit) {
//     return $fruit['count'] >= 2;
// }));

if ($_POST) {
    $valid = TRUE;

    if (empty($_POST['name']) || empty($_POST['count'])) {
        $valid = FALSE;
    }

    if ($valid) {
        $fruit = [
            'name'  => $_POST['name'],
            'count' => $_POST['count']
        ];

        insert('data.json', $fruit);
    }
}
?>
<form method="POST">
    <label>Name:</label>
    <input type="text" name="name">
    <label>Count:</label>
    <input type="number" min="0" name="count">
    <button>Add fruit</button>
</form>
