<?php

require "../repository/AppointmentRepository.php";
require "../domain/Appointment.php";
require "../domain/User.php";
require "../repository/UserRepository.php";

session_start();

$errors = [];

$user = $_SESSION['user'];
$appointmentId = $_GET["appointmentId"];

if (empty($appointmentId)) {
    header('Location: /page/booking.php');
}

$appointmentRepository = new AppointmentRepository();
$appointment = $appointmentRepository->findById($appointmentId);

if (empty($appointment)) {
    header('Location: /page/booking.php');
}

if (empty($user)) {
    header('Location: /page/login.php?appointmentId=' . $_GET["appointmentId"]);
}

if (isset($user) && isset($user->appointment) && $user->userType !== 'ADMIN') {
    header('Location: /page/booking.php');
}

if ($appointment->capacity <= count($appointment->users) && $user->userType !== 'ADMIN') {
    header('Location: /page/booking.php');
}

$userRepository = new UserRepository();
$registeredUsers = $userRepository->findMany(function ($u) use ($appointment) {
    return in_array($u->id, $appointment->users);
});


if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if ($_POST["acceptConditions"] !== 'accept') {
        $errors[] = "A jelentkezéshez el kell fogadnia a regisztrációs feltételeket";
    } else {
        array_push($appointment->users, $user->id);
        $user->appointment = $appointment->id;
        $appointmentRepository->update($appointment->id, $appointment);
        $userRepository->update($user->id, $user);
        $_SESSION['user'] = $user;
        header('Location: /page/booking.php');
    }
}

function getAppointmentDetailHeader() {
    global $appointment;
    if (empty($appointment)) {
        return "";
    }
    return date("Y.m.d", strtotime($appointment->date)) . " " . $appointment->hour . ":00 óra";
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
        <b>Foglalás részletei</b>
    </div>
    <div class="col-12 appointment-title-date"><b> <?= getAppointmentDetailHeader() ?></b></div>
</div>

<?php if (count($errors) > 0): ?>
    <?php foreach ($errors as $error): ?>
        <div class="alert-container text-center">
            <div class="alert alert-danger main-alert mx-auto" role="alert">
                <?= $error ?>
            </div>
        </div>
    <?php endforeach ?>
<?php endif ?>

<?php if ($user->userType !== 'ADMIN'): ?>
    <div class="card">
        <div class="card-body mx-auto">
            <div class="form-group input-group">
                <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-envelope"></i>
            </span>
                </div>
                <div class="form-control"><?= $user->email ?></div>
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-user"></i>
            </span>
                </div>
                <div class="form-control"><?= $user->name ?></div>
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-id-card"></i>
            </span>
                </div>
                <div class="form-control"><?= $user->taj ?></div>
            </div>

            <div class=" form-group input-group">
                <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-map-marker-alt"></i>
            </span>
                </div>
                <div class="form-control"><?= $user->address ?></div>
            </div>

        </div>
        <form method="POST" class="bd-accept-conditions" novalidate>
            <div class="text-center bd-accept-conditions__check ">
                <input type="checkbox" id="acceptConditions" name="acceptConditions" value="accept">
                <label for="acceptConditions">Elfogadom a regisztrációs feltételeket</label><br>
            </div>
            <div class=" text-center">
                <button id="btn-register-form" type="submit" class="btn btn-success"><i class="fa fa-file-alt"></i>Jelentkezés</button>
            </div>
        </form>
    </div>
<?php else: ?>
    <?php if (count($registeredUsers) === 0): ?>
        <div class="alert-container text-center">
            <div class="alert alert-dark main-alert mx-auto" role="alert">
                Még nem érkezett foglalás erre az időpontra
            </div>
        </div>
    <?php endif ?>

    <?php foreach ($registeredUsers as $registeredUser): ?>
        <div class="card admin-card my-3">
            <div class="card-body admin-card-body mx-auto">
                <h4 class="card-title text-center"> <?= $registeredUser->name ?></h4>
                <hr>
                <div class="row my-3">
                    <div class="col-4">
                        <b>Email:</b>
                    </div>
                    <div class="col-8">
                        <p class="card-text"> <?= $registeredUser->email ?></p>
                    </div>
                </div>
                <div class="row my-3">
                    <div class="col-4">
                        <b>Taj:</b>
                    </div>
                    <div class="col-8">
                        <p class="card-text"> <?= $registeredUser->taj ?></p>
                    </div>
                </div>
                <hr>
                <p class="card-text text-center my-2"><b><?= $registeredUser->address ?></b></p>
            </div>
        </div>
    <?php endforeach ?>
<?php endif ?>

</body>

</html>