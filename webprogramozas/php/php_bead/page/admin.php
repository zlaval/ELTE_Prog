<?php

require "../repository/AppointmentRepository.php";
require "../domain/Appointment.php";

session_start();

if (empty($_SESSION['user']) || $_SESSION['user']->userType !== 'ADMIN') {
    header('Location: /index.php');
}

$errors = [];

if ($_POST) {
    if (validate()) {
        $date = getValue('date');
        $hour = intval(getValue('hour'));
        $appointmentRepository = new AppointmentRepository();
        $oldAppointment=$appointmentRepository->findOne(array("date"=>$date,"hour"=>$hour));

        if(isset($oldAppointment)){
            $errors['hour']="Az időpont már hozzáadásra került a naptárhoz!";
        }else{
            $capacity = intval(getValue('capacity'));
            $appointment = new Appointment($capacity, $date, $hour);
            $appointmentRepository->add($appointment);
            header('Location: /page/booking.php');
        }
    }

}

function validate() {
    global $errors;
    isEmpty("date");
    isEmpty("hour");
    isEmpty("capacity");
    validateDate();
    return count($errors) == 0;
}

function validateDate() {
    $date = getValue('date');
    if (isset($date)) {
        $format = 'Y-m-d';
        $dateFormat = DateTime::createFromFormat($format, $date);
        if (!$dateFormat || $dateFormat->format($format) != $date) {
            $errors['date'] = "Hibás dátum!";
        };
    }
}

function isEmpty($fieldName) {
    global $errors;
    $field = $_POST[$fieldName];
    if (empty($field) && $field!=="0") {
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

function selected($value) {
    $origValue = getValue('hour');
    if ($value == $origValue) {
        return "selected";
    } else {
        return "";
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
        <b>Új időpont</b>
    </div>
</div>
<div class="card">
    <form method="POST" class="card-body mx-auto" novalidate>
        <div class="form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-calendar-day"></i>
            </span>
            </div>
            <input type="date" class="form-control" id="date" name="date" placeholder="Dátum" value="<?= getValue('date') ?>">
            <?= printError('date') ?>
        </div>

        <div class="form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-clock"></i>
            </span>
            </div>
            <select name="hour" id="hour" class="form-control" value="<?= getValue('hour') ?>">
                <option></option>
                <option value="0" <?= selected('0') ?>> 00:00 - 01:00</option>
                <option value="1" <?= selected('1') ?>> 01:00 - 02:00</option>
                <option value="2" <?= selected('2') ?>> 02:00 - 03:00</option>
                <option value="3" <?= selected('3') ?>> 03:00 - 04:00</option>
                <option value="4" <?= selected('4') ?>> 04:00 - 05:00</option>
                <option value="5" <?= selected('5') ?>> 05:00 - 06:00</option>
                <option value="6" <?= selected('6') ?>> 06:00 - 07:00</option>
                <option value="7" <?= selected('7') ?>> 07:00 - 08:00</option>
                <option value="8" <?= selected('8') ?>> 08:00 - 09:00</option>
                <option value="9" <?= selected('9') ?>> 09:00 - 10:00</option>
                <option value="10" <?= selected('10') ?>>10:00 - 11:00</option>
                <option value="11" <?= selected('11') ?>>11:00 - 12:00</option>
                <option value="12" <?= selected('12') ?>>12:00 - 13:00</option>
                <option value="13" <?= selected('13') ?>>13:00 - 14:00</option>
                <option value="14" <?= selected('14') ?>>14:00 - 15:00</option>
                <option value="15" <?= selected('15') ?>>15:00 - 16:00</option>
                <option value="16" <?= selected('16') ?>>16:00 - 17:00</option>
                <option value="17" <?= selected('17') ?>>17:00 - 18:00</option>
                <option value="18" <?= selected('18') ?>>18:00 - 19:00</option>
                <option value="19" <?= selected('19') ?>>19:00 - 20:00</option>
                <option value="20" <?= selected('20') ?>>20:00 - 21:00</option>
                <option value="21" <?= selected('21') ?>>21:00 - 22:00</option>
                <option value="22" <?= selected('22') ?>>22:00 - 23:00</option>
                <option value="23" <?= selected('23') ?>>23:00 - 24:00</option>
            </select>
            <?= printError('hour') ?>
        </div>

        <div class="form-group input-group">
            <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="fa fa-users"></i>
            </span>
            </div>
            <input type="number" min="1" class="form-control" id="capacity" name="capacity" placeholder="Férőhely" value="<?= getValue('capacity') ?>">
            <?= printError('capacity') ?>
        </div>

        <div class="text-center">
            <button id="btn-save-appointment" type="submit" class="btn btn-success"><i class="fa fa-save"></i>Mentés</button>
        </div>
    </form>
</div>

<script src="../js/admin.js"></script>
</body>

</html>