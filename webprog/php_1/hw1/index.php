<?php

require 'UserRepository.php';
require 'User.php';

$errors = [];

if ($_POST) {
    validateEmail();
    validatePassword();
    validateBirthYear();
    if (count($errors) == 0) {
        $user = new User($_POST['email'], $_POST['password'], $_POST['birth_year']);
        $userRepository = new UserRepository();
        $userRepository->add($user);
    }


}

function validateEmail() {
    global $errors;
    $email = $_POST['email'];
    if (empty($email)) {
        $errors[] = "Az email cím nem lehet üres!";
    } else {
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $errors[] = "Nem valid email cím";
        }
    }
}

function validatePassword() {
    global $errors;
    $password = $_POST['password'];
    if (empty($password)) {
        $errors[] = "A jelszó nem lehet üres!";
    } else {
        if (strlen($password) < 8) {
            $errors[] = "A jelszónak minimum 8 karaktert kell tartalmaznia";
        }
    }
}

function validateBirthYear() {
    global $errors;
    $by = $_POST['birth_year'];
    if (!empty($by)) {
        if (!filter_var($by, FILTER_VALIDATE_INT)) {
            $errors[] = "A születési év csak szám lehet";
        } else if ($by < 1970) {
            $errors[] = "A születési év nem lehet kisebb 1970-nél";
        }

    }
}

function array_all_keys_exist($array, $inputs) {
    $invalidFields = [];
    foreach ($inputs as $input) {
        if (empty($array[$input])) {
            $invalidFields[] = $input . " nem lehet üres!";
        }
    }

    return $invalidFields;
}


function verify_post(...$inputs) {
    return array_all_keys_exist($_POST, $inputs);
}

function get_form_data($key) {
    global $errors;
    if (count($errors) == 0) {
        return '';
    }
    if (empty($_POST[$key])) {
        return '';
    }
    return $_POST[$key];
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
    <ul>
        <?php foreach ($errors as $error): ?>
            <li><?= $error ?></li>
        <?php endforeach ?>
    </ul>
<?php endif ?>

<form method="POST">
    <label>Email:</label>
    <input type="text" name="email" value="<?= get_form_data('email') ?>">
    <label>Jelszó:</label>
    <input type="password" name="password" value="<?= get_form_data('password') ?>">
    <label>Születési év:</label>
    <input type="number" name="birth_year" value="<?= get_form_data('birth_year') ?>">
    <button>Mentés</button>
</form>
</body>
</html>