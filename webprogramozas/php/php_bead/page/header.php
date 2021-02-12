<?php
session_start();

$user = $_SESSION['user'];

?>
<div class="header-container">
<?php if (empty($user)): ?>
    <div class="buttonRow row header-nav">
        <div class="col home-link-container">
            <a class="home-link" href="/index.php"/> <i class="fa fa-home  fa-3x"></i></a>
            <a class="calendar-link" href="/page/booking.php"/> <i class="fa fa-calendar-check fa-2x"></i></a>
        </div>
        <div class="text-center header-title">
            <h1 class="title"><span class="title-text">NemKoViD</span></h1>
        </div>
        <div class="col header-center">
            <button id="btn-login" type="button" class="btn btn-primary"><i class="fa fa-sign-in-alt"></i>Bejelentkezés</button>
            <button id="btn-registration" type="button" class="btn btn-danger"><i class="fa fa-user-plus"></i>Regisztráció</button>
        </div>
    </div>
<?php else: ?>
    <div class="buttonRow row header-nav">
        <div class="col home-link-container">
            <a class="home-link" href="/index.php"/> <i class="fa fa-home  fa-3x"></i></a>
            <a class="calendar-link" href="/page/booking.php"/> <i class="fa fa-calendar-check fa-2x"></i></a>
        </div>
        <div class="text-center header-title">
            <h1 class="title"><span class="title-text">NemKoViD</span></h1>
        </div>
        <div class="col header-center">
            <span class="header-user-name"><?= $user->name ?></span>
            <button id="btn-logout" type="button" class="btn btn-danger"><i class="fa fa-sign-out-alt"></i>Kijelentkezés</button>
        </div>
    </div>

<?php endif ?>
<hr class="header-line">
</div>

<script src="../js/common.js"></script>