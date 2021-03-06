<?php
require 'Repository.php';

class BookRepository extends BaseRepository {

    public function __construct() {
        parent::__construct(new JsonIO("books.json"),false);
    }

    public function findByAuthor($author) {
        return $this->findAll(["author" => $author]);
    }

}