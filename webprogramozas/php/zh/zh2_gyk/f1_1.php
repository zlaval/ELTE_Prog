<?php

$errors = [];

$response = NULL;

class ResponseClass {
    public $licenceValid = NULL;
    public $licenceExpireAt = NULL;

    public function __construct($licenceValid, $licenceExpireAt) {
        $this->licenceValid = $licenceValid;
        $this->licenceExpireAt = $licenceExpireAt;
    }
}

if ($_POST) {

    $birthDate = getValueFromPost("birth_date");
    $licenceIssued = getValueFromPost("issue_date");
    validateDateAttribute($birthDate, "Birth date");
    validateDateAttribute($licenceIssued, "Licence issued date");

    if ($birthDate > $licenceIssued) {
        $errors[] = "Birth date must be before licence issued date";
    } else if(count($errors) == 0){
        $toDay = date("Y-m-d");

        $ageAtLicenceIssued = $licenceIssued - $birthDate;
        $elapsedYearsFromLicenceIssued = $toDay - $licenceIssued;

        $licenceValid = false;
        $licenceExpireAt = "";
        if ($ageAtLicenceIssued < 60 && $elapsedYearsFromLicenceIssued <= 10) {
            $licenceValid = true;
            $licenceExpireAt =date( "Y-m-d",strtotime('+10 years', strtotime($licenceIssued)));
        } else if ($ageAtLicenceIssued >= 60 && $elapsedYearsFromLicenceIssued <= 5) {
            $licenceValid = true;
            $licenceExpireAt = date("Y-m-d",strtotime('+5 years', strtotime($licenceIssued)));
        }
        $response = new ResponseClass($licenceValid, $licenceExpireAt);
    }
}

function validateDateAttribute($input, $localizedName) {
    global $errors;
    if (!$input) {
        $errors[] = $localizedName . " cannot be empty";
    } else if (!isInputDateFormat($input)) {
        $errors[] = $localizedName . " must be in valid date format";
    }
}

function isInputDateFormat($date) {
    $format = 'Y-m-d';
    $dateFormat = DateTime::createFromFormat($format, $date);
    return $dateFormat && $dateFormat->format($format) == $date;
}

function getValueFromPost($fieldName) {
    return $_POST[$fieldName];
}

function get_form_data($key) {
    if (!isset($_POST[$key])) {
        return '';
    }
    return $_POST[$key];
}

?>
<?php if ($response!=null): ?>
    <ul>
        <?php if ($response->licenceValid): ?>
            <span><?= $response->licenceExpireAt?></span>
        <?php endif ?>
        <?php if (!$response->licenceValid): ?>
            <span>Lejárt</span>
        <?php endif ?>
    </ul>
<?php endif ?>


<?php if (count($errors) > 0): ?>
    <ul>
        <?php foreach ($errors as $error): ?>
            <li><?= $error ?></li>
        <?php endforeach ?>
    </ul>
<?php endif ?>
<form method="POST">
    Születési dátum:
    <input type="date" name="birth_date" value="<?= get_form_data('birth_date') ?>">
    <br>
    Jogosítvány kiállítva:
    <input type="date" name="issue_date" value="<?= get_form_data('issue_date') ?>">
    <button type="submit">Send</button>
</form>