<?php

require 'Repository.php';

class DescendentsRepository extends BaseRepository {

    public function __construct() {
        parent::__construct(new JsonIO("descendents.json"),false);
    }


}
