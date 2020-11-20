<?php

class Book {

    public $id=NULL;
    public $title = NULL;
    public $author = NULL;
    public $publicationYear = NULL;

    public function __construct($title, $author, $publicationYear) {
        $this->title = $title;
        $this->author = $author;
        $this->publicationYear = $publicationYear;
    }

}