<?php

require_once 'baserepository.php';

class UserRepository extends BaseRepository {

    public function __construct() {
        parent::__construct(new JsonIO("../fsdb/user.json"), false);
    }

}