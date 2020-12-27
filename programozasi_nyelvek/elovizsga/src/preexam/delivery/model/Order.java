package preexam.delivery.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderNumber;
    private int zipCode;
    private List<String> orders;

    public Order(int orderNumber, int zipCode, List<String> orders) {
        this.orderNumber = orderNumber;
        this.zipCode = zipCode;
        if (orders == null) {
            this.orders = new ArrayList<>();
        } else {
            this.orders = orders;
        }
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public List<String> getOrders() {
        return orders;
    }
}
