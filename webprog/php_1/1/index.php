<?php

$errors = [];

if ($_POST) {
    // $a = (int)$_POST['a'];
    // $b = (int)$_POST['b'];

    // echo 'K = ' . ($a + $b) . ' T = ' . ($a * $b);

    if (empty($_POST['a'])) {
        array_push($errors, 'The "a" field is required!');
    }
    else if (!is_numeric($_POST['a'])) {
        $errors[] = 'The "a" field is not numeric!';
    }
    else {
        $a = (int)$_POST['a'];

        if ($a <= 0) {
            $errors[] = 'The "a" field must be postive!';
        }
    }
}

function get_form_data($key) {
    if (!isset($_POST[$key])) {
        return '';
    }

    return $_POST[$key];
}

?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Webprogramming - Backend practice</title>
</head>
<body>
<?php if (count($errors) > 0): ?>
    <ul>
        <?php foreach($errors as $error): ?>
            <li><?= $error ?></li>
        <?php endforeach ?>
    </ul>
<?php endif ?>
<form method="POST">
    <label>a:</label>
    <input type="text" name="a" value="<?= get_form_data('a') ?>">
    <label>b:</label>
    <input type="text" name="b" value="<?= get_form_data('b') ?>">
    <button>Submit</button>
</form>
</body>
</html>