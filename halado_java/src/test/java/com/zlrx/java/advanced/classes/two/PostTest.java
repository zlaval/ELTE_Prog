package com.zlrx.java.advanced.classes.two;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class PostTest {

    private MailBox odd = new MailBox();
    private MailBox even = new MailBox();

    private Post underTest = new Post(odd, even);


    @Test
    public void testMailing() {
        List<Mail> testMails = Arrays.asList(
                new Mail(6),
                new Mail(3),
                new Mail(4),
                new Mail(2),
                new Mail(5),
                new Mail(1)
        );

        testMails.forEach(underTest::assort);

        List<Mail> oddMails = testMails.stream()
                .filter(m -> m.getId() % 2 == 1)
                .collect(Collectors.toList());

        List<Mail> evenMails = testMails.stream()
                .filter(m -> m.getId() % 2 == 0)
                .collect(Collectors.toList());

        assertEquals(oddMails, odd.getMails());
        assertEquals(evenMails, even.getMails());
    }


}
