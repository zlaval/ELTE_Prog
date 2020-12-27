<?php

require 'BookRepository.php';
require 'Book.php';
require 'validator.php';

$errors = [];

if ($_POST) {
    $errors = verify_post("title", "author", "producedAt");
    echo count($errors);
    if (count($errors) == 0){
        $bookRepository = new BookRepository();
        $title = $_POST['title'];
        $author = $_POST['author'];
        $producedAt = $_POST['producedAt'];

        $book = new Book($title, $author, $producedAt);
        $arr=[];
        $arr[]=$book;
        echo json_encode($book,JSON_PRETTY_PRINT);
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
<?php if (count($errors) > 0): ?>
    <ul>
        <?php foreach($errors as $error): ?>
            <li><?= $error ?></li>
        <?php endforeach ?>
    </ul>
<?php endif ?>
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