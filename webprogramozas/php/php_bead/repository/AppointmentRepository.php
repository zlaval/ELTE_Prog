<?php

require_once 'baserepository.php';

class AppointmentRepository extends BaseRepository {

    public function __construct() {
        parent::__construct(new JsonIO("../fsdb/appointment.json"), false);
    }
}