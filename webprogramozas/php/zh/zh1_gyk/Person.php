<?php

class Person {

    public $nev = NULL;
    public $csalad = NULL;
    public $kozeli = NULL;

    public function __construct($nev, $csalad, $kozeli) {
        $this->nev = $nev;
        $this->csalad = $csalad;
        $this->kozeli = $kozeli;
    }

}