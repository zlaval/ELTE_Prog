<?php

function array_all_keys_exist($array, $inputs) {
    foreach ($inputs as $input) {
        if (!isset($array[$input])) {
            return FALSE;
        }
    }

    return TRUE;
}

function verify_get(...$inputs) {
    return array_all_keys_exist($_GET, $inputs);
}

function verify_post(...$inputs) {
    return array_all_keys_exist($_POST, $inputs);
}
