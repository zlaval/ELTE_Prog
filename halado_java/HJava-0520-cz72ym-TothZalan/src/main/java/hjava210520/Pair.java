package hjava210520;

public class Pair {
    private final Double value;
    private final Month month;

    public Pair(Double value, Month month) {
        this.value = value;
        this.month = month;
    }

    public Double getValue() {
        return value;
    }

    public Month getMonth() {
        return month;
    }
}
