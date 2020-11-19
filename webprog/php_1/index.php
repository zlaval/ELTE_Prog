<?php

require 'BookRepository.php';
require 'Book.php';
require 'validator.php';

$error = false;
$bookRepository = new BookRepository();

if ($_POST) {
    $error = verify_post($_POST);

    if (!$error) {
        $title = $_POST['title'];
        $author = $_POST['author'];
        $producedAt = $_POST['producedAt'];

        $book = new Book($title, $author, $producedAt);
        $bookRepository->add($book);
    }
}

?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Minta</title>
</head>
<body>
<form method="POST">
    <label>Title:</label>
    <input type="text" name="title">
    <label>Author:</label>
    <input type="text" name="author">
    <label>Produced At:</label>
    <input type="number" name="producedAt">
    <button>Submit</button>
</form>
</body>
</html>