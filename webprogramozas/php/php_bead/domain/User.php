<?php


class User {
    public $id;
    public $name;
    public $taj;
    public $address;
    public $email;
    public $password;
    public $userType;
    public $appointment;

    public function __construct($name, $taj, $address, $email, $password, $userType) {
        $this->name = $name;
        $this->taj = $taj;
        $this->address = $address;
        $this->email = $email;
        $this->password = $password;
        $this->userType = $userType;
    }

}