<?php


class Appointment {
    public $id;
    public $capacity;
    public $date;
    public $hour;
    public $users = [];

    public function __construct($capacity, $date, $hour) {
        $this->capacity = $capacity;
        $this->date = $date;
        $this->hour = $hour;
    }

}