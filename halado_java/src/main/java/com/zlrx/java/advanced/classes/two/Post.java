package com.zlrx.java.advanced.classes.two;

public class Post {

    private MailBox odd;
    private MailBox even;

    public Post(MailBox odd, MailBox even) {
        this.odd = odd;
        this.even = even;
    }

    public void assort(Mail mail) {
        if (mail.getId() % 2 == 0) {
            even.receive(mail);
        } else {
            odd.receive(mail);
        }
    }

}
