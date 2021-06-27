package hjava210520;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        var numberSupplier = new Supplier<Integer>() {
            List<Integer> numbers = List.of(11, 12, 13);
            int index = 0;

            @Override
            public Integer get() {
                return numbers.get(index++);
            }
        };

        var entrySupplier = new Supplier<Map.Entry<String, Integer>>() {
            int num = 2;

            @Override
            public Map.Entry<String, Integer> get() {
                StringBuilder value = new StringBuilder("");
                for (int i = 0; i < Integer.toBinaryString(num).length(); ++i) {
                    value.append("a");
                }
                var result = Map.of(value.toString(), num).entrySet().stream().findFirst().orElseThrow(RuntimeException::new);
                num *= 2;
                return result;
            }
        };

        startServer(1000, numberSupplier);
        startServer(1001, entrySupplier);


        Thread.sleep(100);

        startClient(1001, List.of("aa=2", "aaa=4", "aaaa=8", "aaaaa=16", "aaaaaa=32"));
        startClient(1000, List.of("11", "12", "13"));

    }

    public static <T> void startServer(int port, Supplier<T> textGenerator) {
        Thread thread = new Thread(() -> {
            try (
                    var serverSocket = new ServerSocket(port);
                    var connection = serverSocket.accept();
                    var scanner = new Scanner(connection.getInputStream());
                    var writer = new PrintWriter(connection.getOutputStream())
            ) {
                var numberOfMessages = Integer.valueOf(scanner.nextLine());
                for (int i = 0; i < numberOfMessages; ++i) {
                    var line = scanner.nextLine();
                    var expectedLine = textGenerator.get().toString();
                    if (line.equals(expectedLine)) {
                        writer.println("ok");
                    } else {
                        writer.println("wrong " + expectedLine);
                    }
                    writer.flush();
                }
                writer.println("end");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static void startClient(int port, List<String> text) {
        try (
                var socket = new Socket("localhost", port);
                var scanner = new Scanner(socket.getInputStream());
                var writer = new PrintWriter(socket.getOutputStream())
        ) {
            int numberOfMessages = text.size();
            writer.println(numberOfMessages);
            for (String msg : text) {
                writer.println(msg);
                writer.flush();
                var read = scanner.nextLine();
                if (!read.equals("ok")) {
                    System.err.println(read);
                }
            }
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            } else {
                System.err.println("No end comes");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
