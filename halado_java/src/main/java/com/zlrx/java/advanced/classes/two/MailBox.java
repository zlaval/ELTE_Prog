package com.zlrx.java.advanced.classes.two;

import java.util.ArrayList;
import java.util.List;

public class MailBox {

    private List<Mail> mails = new ArrayList<>();

    public void receive(Mail mail) {
        mails.add(mail);
    }

    public List<Mail> getMails() {
        return mails;
    }
}
