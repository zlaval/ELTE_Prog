package elte.concurrent.zh;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiamondBusiness {

    public static void main(String[] args) {
        String[] names = {"A", "B", "C", "D", "E", "F", "G"};

        CountDownLatch countDownLatch = new CountDownLatch(1);

        var jewelleries = List.of(
                new Jewellery(
                        10_000, 1, names[0]
                ),

                new Jewellery(
                        100_000, 3, names[1]
                ),
                new Jewellery(
                        1_000_000, 5, names[2]
                )
        );

        jewelleries.forEach(Jewellery::start);

        var diamondAgents = IntStream.range(0, 5).mapToObj(i -> new DiamondAgent(jewelleries, names[i])).collect(Collectors.toList());
        diamondAgents.forEach(DiamondAgent::start);
        
        ScheduledExecutorService buyerExecutorService = Executors.newScheduledThreadPool(100);
        buyerExecutorService.scheduleAtFixedRate(() -> {
            var buyer = new Buyer(jewelleries);
            buyer.tryBuy();
        }, 0, 5, TimeUnit.MILLISECONDS);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buyerExecutorService.shutdownNow();
    }


}
