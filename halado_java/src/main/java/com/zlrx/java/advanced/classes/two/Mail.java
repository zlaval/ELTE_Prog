package com.zlrx.java.advanced.classes.two;

import java.util.Objects;

public class Mail {

    private int id;

    public Mail(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return id == mail.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
