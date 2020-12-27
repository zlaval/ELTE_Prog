<?php
include "word_diff.php";
session_start();

if (empty($_SESSION["previous"])) {
    $_SESSION["previous"] = "";
}

$result = [];
if ($_POST) {
    $actual = $_POST["text"];
    $previous = $_SESSION["previous"];
    $result = string_diff($previous, $actual);
    session_unset();
    $_SESSION["previous"] = $actual;
}

?>

<style>form {
        display: flex
    }

    textarea, div, button {
        flex: 1 1;
        height: 100px;
    }</style>
<form method="post">
    <textarea id="input" name="text"><?= $_SESSION["previous"] ?></textarea>
    <div disabled>
        <?php if (count($result) == 0): ?>
            <?= $_SESSION["previous"] ?>
        <?php else: ?>
            <?php foreach ($result as $text): ?>
                <?php if (is_array($text) == 0): ?>
                    <?= $text ?>
                <?php else: ?>
                    <?php foreach ($text as $type => $word): ?>
                        <?php if ($type == 'ins'): ?>
                            <ins><?= $word ?></ins>
                        <?php else: ?>
                            <del> <?= $word ?></del>
                        <?php endif ?>
                    <?php endforeach ?>
                <?php endif ?>
            <?php endforeach ?>
        <?php endif ?>

    </div>
    <button type="submit">Send</button>
</form>