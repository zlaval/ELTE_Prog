<?php

require 'filehandler.php';

interface Repository {

    function add($record): string;

    function findById(string $id);

    function findAll(array $params = []);

    function findOne(array $params = []);

    function update(string $id, $record);

    function findMany(callable $condition);

}

abstract class BaseRepository implements Repository {

    protected $contents;
    protected $io;

    public function __construct(FileIO $io, $assoc = true) {
        $this->io = $io;
        $this->contents = (array)$this->io->load($assoc);
    }

    public function __destruct() {
        $this->io->save($this->contents);
    }

    public function add($record): string {
        $id = uniqid();
        if (is_array($record)) {
            $record['id'] = $id;
        } else if (is_object($record)) {
            $record->id = $id;
        }
        $this->contents[$id] = $record;
        return $id;
    }

    public function findAll(array $params = []) {
        return array_filter($this->contents, function ($item) use ($params) {
            foreach ($params as $key => $value) {
                if (((array)$item)[$key] !== $value) {
                    return FALSE;
                }
            }
            return TRUE;
        });
    }

    public function findOne(array $params = []) {
        $found_items = $this->findAll($params);
        $first_index = array_keys($found_items)[0] ?? NULL;
        return $found_items[$first_index] ?? NULL;
    }

    public function findById(string $id) {
        return $this->contents[$id];
    }

    public function update($id, $record) {
        $this->contents[$id] = $record;
    }

    public function findMany(callable $condition) {
        return array_filter($this->contents, $condition);
    }

}