<?php


class Book {

    private $id=NULL;
    private $title = NULL;
    private $author = NULL;
    private $publicationYear = NULL;

    public function __construct($title, $author, $publicationYear) {
        $this->title = $title;
        $this->author = $author;
        $this->publicationYear = $publicationYear;
    }

    public function getTitle() {
        return $this->title;
    }

    public function setTitle($title) {
        $this->title = $title;
    }

    public function getAuthor() {
        return $this->author;
    }

    public function setAuthor($author) {
        $this->author = $author;
    }

    public function getPublicationYear() {
        return $this->publicationYear;
    }

    public function setPublicationYear($publicationYear) {
        $this->publicationYear = $publicationYear;
    }

    public function getId() {
        return $this->id;
    }

    public function setId($id) {
        $this->id = $id;
    }



}