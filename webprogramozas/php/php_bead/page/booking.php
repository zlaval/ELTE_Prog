<?php

require "../repository/AppointmentRepository.php";
require "../domain/Appointment.php";
require "../domain/User.php";
require "../repository/UserRepository.php";
require "../model/BookingTable.php";

session_start();

if (empty($_SESSION['calendarDate'])) {
    $date = new DateTime();
    $_SESSION['calendarDate'] = $date;
}
$user = $_SESSION['user'];


$appointmentRepository = new AppointmentRepository();
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $appointmentId = $user->appointment;
    $userRepository = new UserRepository();
    $user = $userRepository->findById($user->id);
    $user->appointment = NULL;
    $userRepository->update($user->id, $user);
    $appointment = $appointmentRepository->findById($appointmentId);

    $index = array_search($user->id, $appointment->users);
    if ($index !== FALSE) {
        unset($appointment->users[$index]);
    }
    $appointmentRepository->update($appointmentId, $appointment);
    $_SESSION['user'] = $user;
}

$userReservation = null;
if (isset($user) && isset($user->appointment)) {
    $userAppointment = $appointmentRepository->findById($user->appointment);
    $userReservation = "Az ön foglalása: <b>" . date("Y.m.d", strtotime($userAppointment->date)) . " " . $userAppointment->hour . ":00 óra.</b>";
}

$date = $_SESSION['calendarDate'];
$calendarMonth = $date->format('Y.m');
if ($_GET && $_GET['direction']) {

    $direction = $_GET["direction"];
    if ($direction === 'next') {
        $date->modify("+1 months");
    } else if ($direction === 'prev') {
        $date->modify("-1 months");
    }

    $_SESSION['calendarDate'] = $date;
    $calendarMonth = $date->format('Y.m');
    $monthStart = $date->format('Y-m-01');
    $monthEnd = $date->format('Y-m-t');

    $appointmentsThisMonth = $appointmentRepository->findMany(function ($appointment) use ($monthStart, $monthEnd) {
        return $appointment->date >= $monthStart && $appointment->date <= $monthEnd;
    });

    $bookingData = [];
    $actualDate = clone $date;
    $subtractDay = intval($actualDate->format("d"));
    $actualDate->modify("-{$subtractDay} days");

    for ($i = 1; $i <= intval($date->format('t')); $i++) {
        $actualDate = date_modify($actualDate, '+1 day');
        $dayName = $actualDate->format("l");
        $bookingData[$i] = new BookingTable($i, $dayName, $calendarMonth);
    }

    uasort($appointmentsThisMonth, function ($a, $b) {
        if ($a->hour === $b->hour) {
            return 0;
        }
        return ($a->hour < $b->hour) ? -1 : 1;
    });

    foreach ($appointmentsThisMonth as $appointment) {
        $dayNumber = intval(date("d", strtotime($appointment->date)));
        $freeSpaces = $appointment->capacity - count($appointment->users);
        $reservationCount = count($appointment->users);
        $appointmentData = new AppointmentData($appointment->id, $appointment->hour, $appointment->capacity, $reservationCount, $freeSpaces);
        array_push($bookingData[$dayNumber]->appointMents, $appointmentData);
    }

    echo json_encode(array_values($bookingData));
    exit;
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
        <b>Foglalás</b>
    </div>
</div>
<?php if (isset($userReservation)): ?>
    <form id="cancelReservation" method="POST" novalidate>
        <div class="booking-user-appointment">
            <?= $userReservation ?>
            <button id="btn-cancel-reservation" type="submit" class="btn btn-danger"><i class="fa fa-times"></i>Lemondás</button>
        </div>
    </form>
<?php endif ?>

<div class="booking-calendar">
    <table class="booking-calendar-table">
        <tr class="booking-calendar-table-header">
            <th class="text-center">Hétfő</th>
            <th class="text-center">Kedd</th>
            <th class="text-center">Szerda</th>
            <th class="text-center">Csütörtök</th>
            <th class="text-center">Péntek</th>
            <th class="text-center">Szombat</th>
            <th class="text-center">Vasárnap</th>
        </tr>
        <tbody class="booking-calendar-table-body">

        </tbody>


    </table>

</div>

<div class="row booking-month-step">
    <div class="booking-prev-month col">
        <button id="btn-prev-month" type="button" class="btn btn-link"><i class="fa fa-backward"></i>Előző hónap</button>
    </div>
    <div class="booking-month col text-center">
        <?= $calendarMonth ?>
    </div>
    <div class="booking-next-month col text-right">
        <button id="btn-next-month" type="button" class="btn btn-link">Következő hónap<i class="fa fa-left fa-forward"></i></button>
    </div>

</div>

<?php if (isset($_SESSION['user']) && $_SESSION['user']->userType === 'ADMIN'): ?>
    <div class="buttonRow row">
        <div class="col text-center">
            <button id="btn-admin" type="button" class="btn btn-primary"><i class="fa fa-plus"></i>Új időpont meghirdetése</button>
        </div>
    </div>
<?php endif ?>

<script src="../js/booking.js"></script>
</body>

</html>