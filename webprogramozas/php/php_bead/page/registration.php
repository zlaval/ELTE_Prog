<?php

require "../repository/UserRepository.php";
require "../domain/User.php";

session_start();

$errors = [];

if ($_POST) {

    $userRepository = new UserRepository();
    if (validateFields($userRepository)) {
        $email = getValue("email");
        $name = getValue("name");
        $address = getValue("address");
        $taj = getValue("taj");
        $password = getValue("password");
        $passwordHash = password_hash($password, PASSWORD_BCRYPT);
        $user = new User($name, $taj, $address, $email, $passwordHash, "USER");
        $userRepository->add($user);
        header('Location: /page/login.php');
    }
}

function validateFields($userRepository) {
    global $errors;
    isEmpty("name");
    isEmpty("address");
    isEmpty("password");
    isEmpty("confirmPassword");
    validateEmail($userRepository);
    validateTaj();
    validatePassword();
    return count($errors) == 0;
}

function validatePassword() {
    global $errors;
    $password = getValue("password");
    $passwordConfirm = getValue("confirmPassword");
    if (!empty($password) && !empty($passwordConfirm)) {
        if ($passwordConfirm != $password) {
            $errors["confirmPassword"] = "Nem egyező jelszó!";
        }
    }
}

function validateEmail($userRepository) {
    global $errors;

    if (!isEmpty("email")) {
        $email = getValue("email");
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $errors["email"] = "Az email cím formátuma hibás!";
        }else{
            $userByEmail= $userRepository->findOne(["email" => $email]);
            if(!empty($userByEmail)){
                $errors["email"] = "Az email cím foglalt!";
            }
        }
    }
}

function validateTaj() {
    global $errors;
    if (!isEmpty("taj")) {
        $taj = getValue("taj");
        if (!preg_match("/^\d+$/", $taj)) {
            $errors["taj"] = "Csak számjegyek adhatók meg!";
        } elseif (strlen($taj) != 9) {
            $errors["taj"] = "A mezőbe pontosan 9 számjegyet kell megadni!";
        }
    }
}

function isEmpty($fieldName) {
    global $errors;
    $field = $_POST[$fieldName];
    if (empty($field)) {
        $errors[$fieldName] = "A mező kitöltése kötelező!";
        return true;
    }
    return false;
}

function getValue($fieldName) {
    return $_POST[$fieldName];
}

function printError($fieldName) {
    global $errors;
    if (count($errors) == 0) {
        return "";
    } else if (empty($errors[$fieldName])) {
        return "";
    }
    return '<div class="registration-warning align-text-bottom"> <i class="fa fa-exclamation-triangle registration-warning-icon"></i><span class="registration-warning-text">' . $errors[$fieldName] . '</span></div>';
}

?>

<!DOCTYPE html>
<html lang="hu">

<head>
    <title>Set</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/css/all.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Permanent+Marker&family=Shadows+Into+Light&display=swap" rel="stylesheet">
</head>
<header>
    <?php include "./header.php" ?>
</header>
<body>
<div class="row text-center">
    <div class="col-12 subpage-title">
        <b>Regisztáció</b>
    </div>
</div>
<div class="card">
    <form method="POST" class="card-body mx-auto" novalidate>
        <div class="form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-envelope"></i>
            </span>
            </div>
            <input type="email" class="form-control" id="email" name="email" placeholder="Email cím megadása" value="<?= getValue('email') ?>">
            <?= printError('email') ?>
        </div>
        <div class="form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-user"></i>
            </span>
            </div>
            <input type="text" class="form-control" id="name" name="name" placeholder="Név megadása" value="<?= getValue('name') ?>">
            <?= printError('name') ?>
        </div>
        <div class="form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-id-card"></i>
            </span>
            </div>
            <input type="number" maxlength="9" class="form-control" id="taj" name="taj" placeholder="TAJ szám megadása" value="<?= getValue('taj') ?>">
            <?= printError('taj') ?>
        </div>

        <div class=" form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-map-marker-alt"></i>
            </span>
            </div>
            <input type="text" class="form-control" id="address" name="address" placeholder="Cím megadása" value="<?= getValue('address') ?>">
            <?= printError('address') ?>
        </div>

        <div class=" form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-lock"></i>
            </span>
            </div>
            <input type="password" class="form-control" id="password" name="password" placeholder="Jelszó megadása" value="<?= getValue('password') ?>">
            <?= printError('password') ?>
        </div>
        <div class=" form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-unlock"></i>
            </span>
            </div>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Jelszó megerősítése"
                   value="<?= getValue('confirmPassword') ?>">
            <?= printError('confirmPassword') ?>
        </div>
        <div class=" text-center">
            <button id="btn-register-form" type="submit" class="btn btn-success"><i class="fa fa-user-plus"></i>Regisztál</button>
        </div>
    </form>
</div>

</body>

</html>