<?php

class AppointmentData {
    public $id;
    public $time;
    public $maxCapacity;
    public $reservationCount;
    public $freeSpaces;

    public function __construct($id, $time, $maxCapacity, $reservationCount,$freeSpaces) {
        $this->id = $id;
        $this->time = $time;
        $this->maxCapacity = $maxCapacity;
        $this->reservationCount = $reservationCount;
        $this->freeSpaces=$freeSpaces;
    }

}

class BookingTable {

    public $calendarMonth;
    public $dayInMonth;
    public $dayName;
    public $appointMents=[];

    public function __construct($dayInMonth, $dayName,$calendarMonth) {
        $this->calendarMonth=$calendarMonth;
        $this->dayInMonth = $dayInMonth;
        $this->dayName = $dayName;
    }

}