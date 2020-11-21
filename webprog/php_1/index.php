<?php

class User {
    public $email;
    public $password;
    public $birthYear;

    public function __construct($email, $password, $birthYear) {
        $this->email = $email;
        $this->password = $password;
        $this->birthYear = $birthYear;
    }

}

class UserRepository extends BaseRepository {
    public function __construct() {
        parent::__construct(new JsonIO("users.json"), false);
    }
}

abstract class BaseRepository {

    protected $contents;
    protected $io;

    public function __construct(JsonIO $io, $assoc = true) {
        $this->io = $io;
        $this->contents = (array)$this->io->load($assoc);
    }

    public function __destruct() {
        $this->io->save($this->contents);
    }

    public function add($record): string {
        $id = uniqid();
        if (is_array($record)) {
            $record['id'] = $id;
        } else if (is_object($record)) {
            $record->id = $id;
        }
        $this->contents[] = $record;
        return $id;
    }

}

class JsonIO {

    private $filepath;

    public function __construct($filename) {
        if (!is_readable($filename) || !is_writable($filename)) {
            throw new Exception("Data source ${filename} is invalid.");
        }
        $this->filepath = realpath($filename);
    }

    public function load($assoc = true) {
        $file_content = file_get_contents($this->filepath);
        return json_decode($file_content, $assoc) ?: [];
    }

    public function save($data) {
        $json_content = json_encode($data, JSON_PRETTY_PRINT);
        file_put_contents($this->filepath, $json_content);
    }
}

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