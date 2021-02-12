<?php

interface FileIO {

    function save($data);

    function load($assoc );
}

abstract class AbstractFileIO implements FileIO {

    protected $filepath;

    public function __construct($filename) {
        if (!is_readable($filename) || !is_writable($filename)) {
            throw new Exception("Data source ${filename} is invalid.");
        }
        $this->filepath = realpath($filename);
    }
}

class JsonIO extends AbstractFileIO {

    public function load($assoc = true) {
        $file_content = file_get_contents($this->filepath);
        return json_decode($file_content, $assoc) ?: [];
    }

    public function save($data) {
        $json_content = json_encode($data, JSON_PRETTY_PRINT);
        file_put_contents($this->filepath, $json_content);
    }
}