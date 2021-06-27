package com.zlrx.concurrent;

import com.zlrx.concurrent.model.Customer;
import com.zlrx.concurrent.model.CustomerStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Statistics {

    private final List<Customer> customerList = Collections.synchronizedList(new ArrayList<>());

    public void addStat(Customer customer) {
        customerList.add(customer);
    }

    public void printResult() {
        synchronized (customerList) {
            customerList.stream()
                    .collect(Collectors.groupingBy(Customer::getStatus))
                    .forEach((type, values) -> {
                        var numberOfCustomers = values.size();
                        System.out.println(numberOfCustomers + type.getTitle());
                    });
            var averageWaitingTime = customerList.stream()
                    .filter(c -> c.getStatus() == CustomerStatus.SUCCESS)
                    .mapToLong(Customer::getWaitTime)
                    .summaryStatistics()
                    .getAverage();
            System.out.println("Average waiting time of the served customers was " + averageWaitingTime + " ms.");

            customerList.stream()
                    .filter(c -> c.getStatus() == CustomerStatus.SUCCESS)
                    .collect(Collectors.groupingBy(Customer::getDay))
                    .forEach((day, values) -> {
                        var numberOfCustomers = values.size();
                        System.out.println(numberOfCustomers + " customers were served at day " + day);
                    });
        }
    }

}
