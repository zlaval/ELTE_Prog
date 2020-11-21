<?php
require 'Repository.php';

class UserRepository extends BaseRepository {
    public function __construct() {
        parent::__construct(new JsonIO("users.json"), false);
    }
}