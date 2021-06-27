package hjava210520;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;


class Counter {
    int index = 0;

    public int getAndIncrement() {
        return index++;
    }
}

public class ThirdMain {

    //private static final String FILE_NAME = "10ppm_data.txt";
    private static final String FILE_NAME = "D:\\Application\\ELTE\\elte-prog-private\\HJava-0520-cz72ym-TothZalan\\src\\main\\resources\\10ppm_data.txt";

    public Optional<Pair> getFirstMonthByYield(int expectedAssets) throws IOException {
        double expected = (double) expectedAssets / 100.0;
        Optional<Pair> result = Files.readAllLines(Paths.get(FILE_NAME))
                .stream()
                .flatMap(this::split)
                .map(this::calculateAsset)
                .map(p -> calculator.apply(p))
                .filter(p -> p.getValue() >= expected)
                .findFirst();
        return result;
    }

    private Function<Pair, Pair> calculator = new Function<>() {
        private Double lastValue = 1.0;

        @Override
        public Pair apply(Pair pair) {
            Double y = pair.getValue() * lastValue;
            lastValue = y;
            return new Pair(y, pair.getMonth());
        }
    };

    private Pair calculateAsset(Pair input) {
        double multiplier = 100.0 + input.getValue();
        double value = multiplier / 100;
        return new Pair(value, input.getMonth());
    }

    private Stream<Pair> split(String line) {
        Comparator<Pair> c = (a, b) -> b.getMonth().ordinal() - a.getMonth().ordinal();

        var index = new Counter();
        return Arrays.stream(line.split(" "))
                .map(this::createNumber)
                .map(v -> new Pair(v, Month.values()[index.getAndIncrement()]))
                .sorted(c);
    }

    private Double createNumber(String value) {
        var replaced = value.replace("%", "");
        if (replaced.contains("(")) {
            replaced = replaced.replace("(", "").replace(")", "");
            return Double.parseDouble(replaced) * -1.0;
        }
        return Double.parseDouble(replaced);
    }


}
