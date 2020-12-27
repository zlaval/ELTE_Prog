package preexam.delivery;

import preexam.delivery.exceptions.WrongZipCodeException;
import preexam.delivery.model.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class DeliveryService {
    private String name;
    protected int zipCode;
    protected List<Integer> servedZipCodes;
    private final static AtomicInteger orderNumberGenerator = new AtomicInteger(0);
    private Map<Integer, Order> orderStore = new HashMap<>();


    public DeliveryService(String name, int zipCode) {
        this.name = name;
        this.zipCode = zipCode;
        this.servedZipCodes = new ArrayList<>();
    }

    public DeliveryService(String name, int zipCode, Integer... zips) {
        this.name = name;
        this.zipCode = zipCode;
        this.servedZipCodes = Arrays.asList(zips);
    }

    public DeliveryService(String fileName) {
        this.servedZipCodes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            int lineNumber = 1;
            while (line != null) {
                String[] data = line.split(",");
                if (lineNumber == 1) {
                    this.name = data[0];
                    this.zipCode = Integer.parseInt(data[1]);
                    if (data.length > 2) {
                        for (int i = 2; i < data.length; i++) {
                            servedZipCodes.add(Integer.parseInt(data[i]));
                        }
                    }
                } else {
                    var orderNumber = orderNumberGenerator.incrementAndGet();
                    List<String> orders = new ArrayList<>();
                    if (data.length > 1) {
                        for (int i = 1; i < data.length; i++) {
                            orders.add(data[i]);
                        }
                    }
                    orderStore.put(orderNumber, new Order(orderNumber, Integer.parseInt(data[0]), orders));
                }
                lineNumber++;
                line = br.readLine();
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("file does not exist");
        }
    }

    public int prepareOrder(int orderZipCode, String... orders) {
        if (checkZipCode(orderZipCode)) {
            var orderNumber = orderNumberGenerator.incrementAndGet();
            var order = new Order(orderNumber, orderZipCode, Arrays.asList(orders));
            orderStore.put(orderNumber, order);
            return orderNumber;
        }
        throw new WrongZipCodeException(orderZipCode);
    }

    public void cancelOrder(int orderNumber) {
        if (orderStore.containsKey(orderNumber)) {
            orderStore.remove(orderNumber);
        } else {
            throw new IllegalArgumentException("Unknown order " + orderNumber);
        }
    }

    public Map<Integer, Map<String, Integer>> makeDeliveries() {
        var result = new HashMap<Integer, Map<String, Integer>>();
        orderStore.forEach((orderNumber, order) -> {
            if (!order.getOrders().isEmpty()) {
                var orderZip = order.getZipCode();
                var orderOnZip = result.getOrDefault(orderZip, new HashMap<>());
                result.putIfAbsent(orderZip, orderOnZip);
                var orders = order.getOrders();
                for (var o : orders) {
                    orderOnZip.putIfAbsent(o, 0);
                    var count = orderOnZip.get(o);
                    orderOnZip.replace(o, count + 1);
                }
            }
        });
        orderStore.clear();
        return result;
    }

    protected boolean checkZipCode(int orderZipCode) {
        return zipCode == orderZipCode || servedZipCodes.contains(orderZipCode);
    }

    public ProductState getProductState(String product) {
        int orderNumber = 0;
        for (Order order : orderStore.values()) {
            orderNumber += order.getOrders().stream()
                    .filter(o -> o.equals(product))
                    .count();
        }
        switch (orderNumber) {
            case 0:
                return ProductState.NO_ORDER;
            case 1:
                return ProductState.SINGLE_ORDER;
            default:
                return ProductState.MULTI_ORDER;
        }
    }

    public String getName() {
        return name;
    }

    public int getZipCode() {
        return zipCode;
    }

    public List<Integer> getServedZipCodes() {
        return servedZipCodes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DeliveryService that = (DeliveryService) obj;
        return zipCode == that.zipCode &&
                Objects.equals(name, that.name) &&
                servedZipCodes.containsAll(that.servedZipCodes) &&
                that.servedZipCodes.containsAll(servedZipCodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, zipCode, servedZipCodes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DS[");
        sb.append(name);
        sb.append(", ");
        sb.append(zipCode);
        sb.append(", ");
        for (var zip : servedZipCodes) {
            sb.append(zip);
            sb.append(", ");
        }
        sb.append(orderStore.size());
        sb.append("]");
        return sb.toString();
    }
}
