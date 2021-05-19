package com.zlrx.java.advanced.classes.five;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LambdaTest {
    private static class Message {
        public static final int MAX_LENGTH = 5;

        private final byte[] data;
        private final int order;

        private Message(byte[] data, int order) {
            if (data.length > MAX_LENGTH) throw new IllegalArgumentException("size too big");
            this.data = data;
            this.order = order;
        }

        public byte[] getData() {
            return data;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Message.class.getSimpleName() + "[", "]")
                    .add("data=" + Arrays.toString(data))
                    .add("order=" + order)
                    .toString();
        }
    }

    public static void main(String[] args) {
        String input = "akofd98sadasvcpaaud jfapsiudfaösdfiasdfasfduaspdfapsodf";
        byte[] data = input.getBytes(StandardCharsets.UTF_8); //ENCODE BASE64 -> BYTE
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < data.length; i = i + Message.MAX_LENGTH) { //HF -> transform to stream based solution
            byte[] slice = Arrays.copyOfRange(data, i, Math.min(data.length, i + Message.MAX_LENGTH));
            messages.add(new Message(slice, messages.size()));
        }


        //SEND --
        List<Message> receivedMessages = messages.stream().collect(Collectors.toList()); //FIXME: randomized order
        Collector<byte[], ByteArrayOutputStream, byte[]> coll = Collector.of(ByteArrayOutputStream::new,
                (accu, arr) -> {
                    try {
                        accu.write(arr);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                },
                (buff1, buff2) -> {
                    try {
                        buff2.writeTo(buff1);
                        return buff1;
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                },
                ByteArrayOutputStream::toByteArray);
        byte[] res = receivedMessages.stream().map(m -> m.data).collect(coll);
        String x = new String(res, StandardCharsets.UTF_8);
        System.out.println(x);
        System.out.println(x.equals(input));
    }
}
