<?php

require 'DescendentsRepository.php';
require 'Person.php';

$errors = [];
$filteredDescendents = [];

if ($_POST) {

    $errors = verify_post('ancient_family_name', 'relatives');
    if (count($errors) == 0) {
        $descendentsRepository = new DescendentsRepository();
        $descendents = $descendentsRepository->findMany(function ($person) {
            return $person->csalad === $_POST['ancient_family_name'];
        });

        if (!empty($_POST['query_expression'])) {
            $descendents = array_filter($descendents, function ($person) {
                return strpos($person->nev, $_POST['query_expression']) != false;
            });
        }

        if (count($descendents) != 0) {
            if ($_POST['relatives'] == 'all_relatives') {
                $filteredDescendents = $descendents;
            } else if ($_POST['relatives'] == 'close_relatives') {
                $filteredDescendents = array_filter($descendents, function ($person) {
                    return $person->kozeli;
                });
            } else if ($_POST['relatives'] == 'distant_relatives') {
                $filteredDescendents = array_filter($descendents, function ($person) {
                    return !$person->kozeli;
                });
            }

        } else {
            $errors[] = "Nem található ilyen családnév";
        }

    }

}

function array_all_keys_exist($array, $inputs) {
    $invalidFields = [];
    foreach ($inputs as $input) {
        if (empty($array[$input])) {
            $invalidFields[] = $input;
        }
    }

    return $invalidFields;
}


function verify_post(...$inputs) {
    return array_all_keys_exist($_POST, $inputs);
}

function get_form_data($key) {
    if (empty($_POST[$key])) {
        return '';
    }
    return $_POST[$key];
}

function get_form_checked($key, $value) {
    return get_form_data($key) == $value;
}

?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Webprogramming</title>
</head>
<body>
<?php if (count($errors) > 0): ?>
    A kövezkező mezők nem lehetnek üresek:
    <ul>
        <?php foreach ($errors as $error): ?>
            <li><?= $error ?></li>
        <?php endforeach ?>
    </ul>
<?php endif ?>
<?php if (count($filteredDescendents) > 0): ?>
    Találatok:
    <ul>
        <?php foreach ($filteredDescendents as $person): ?>
            <li style="background-color: <?= $person->kozeli ? 'green' : 'red' ?>"><?= $person->nev ?></li>
        <?php endforeach ?>
    </ul>
<?php endif ?>
<form method="POST">
    <label>Ősi családnév:</label>
    <input type="text" name="ancient_family_name" value="<?= get_form_data('ancient_family_name') ?>">
    <label>Kereső kifejezés:</label>
    <input type="text" name="query_expression" value="<?= get_form_data('query_expression') ?>">

    <div>
        <label>Mindenki:</label>
        <input type="radio" value="all_relatives" name="relatives" <?= get_form_checked('relatives', 'all_relatives') ? "checked" : "" ?>>
        <label>Közeli rokonok:</label>
        <input type="radio" value="close_relatives" name="relatives" <?= get_form_checked('relatives', 'close_relatives') ? "checked" : "" ?>>
        <label>Távoli rokonok:</label>
        <input type="radio" value="distant_relatives" name="relatives" <?= get_form_checked('relatives', 'distant_relatives') ? "checked" : "" ?>>
    </div>

    <button>Rokonok keresése</button>
</form>
</body>
</html>