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