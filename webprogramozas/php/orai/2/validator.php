<?php

function array_all_keys_exist($array, $inputs) {
    $errors = [];
    foreach ($inputs as $input) {
        if (empty($array[$input])) {
            $errors[] = $input;
        }
    }

    return $errors;
}

function verify_get(...$inputs) {
    return array_all_keys_exist($_GET, $inputs);
}

function verify_post(...$inputs) {
    return array_all_keys_exist($_POST, $inputs);
}
