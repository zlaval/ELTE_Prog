<?php
require "../repository/UserRepository.php";
require "../domain/User.php";

session_start();

$errors = [];

if (isset($_SESSION['user'])) {
    header('Location: /index.php');
}

if ($_POST) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $userRepository = new UserRepository();
    $user = $userRepository->findOne(["email" => $email]);

    if (empty($user) || empty($password)) {
        $errors[] = "Hibás felhasználónév vagy jelszó";
    } else {
        $valid = password_verify($password, $user->password);
        if (!$valid) {
            $errors[] = "Hibás felhasználónév vagy jelszó";
        } else {
            $_SESSION['user'] = $user;

            if (isset($_GET['appointmentId'])) {
                $queryParam = "?appointmentId=" . $_GET['appointmentId'];
                header('Location: /page/bookingdetail.php' . $queryParam);
            } else {
                header('Location: /page/booking.php');
            }

        }
    }
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
        <b>Bejelentkezés</b>
    </div>
</div>

<?php if (count($errors) > 0): ?>
    <?php foreach ($errors as $error): ?>
        <div class="alert-container text-center">
            <div class="alert alert-primary main-alert mx-auto" role="alert">
                <?= $error ?>
            </div>
        </div>
    <?php endforeach ?>
<?php endif ?>

<div class="card">
    <form method="POST" class="card-body mx-auto" novalidate>
        <div class="form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-envelope"></i>
            </span>
            </div>
            <input type="email" class="form-control" id="email" name="email" placeholder="Email cím">
        </div>

        <div class="form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-lock"></i>
            </span>
            </div>
            <input type="password" class="form-control" id="password" name="password" placeholder="Jelszó">
        </div>
        <div class="text-center">
            <button id="btn-login-form" type="submit" class="btn btn-success"><i class="fa fa-sign-in-alt"></i>Bejelentkezés</button>
        </div>
    </form>
</div>

</body>

</html>