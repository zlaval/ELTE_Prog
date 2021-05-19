package com.zlrx.java.advanced.classes.cls20210421;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

public class ApplyAssocFn {

    private ExecutorService executorService;

    public void calculate() throws ExecutionException, InterruptedException {
        executorService = Executors.newFixedThreadPool(4);
        var result = applyAssoc(List.of(1, 2, 3, 4, 5), Integer::sum);
        System.out.println(result);
        executorService.shutdownNow();
    }

    private <T> T applyAssoc(List<? extends T> values, BiFunction<T, T, T> fn) throws ExecutionException, InterruptedException {
        if (values.size() == 1) {
            return values.get(0);
        }

        int middle = values.size() / 2;
        List<? extends T> lower = values.subList(0, middle);
        List<? extends T> upper = values.subList(middle, values.size());

        Future<T> first = executorService.submit(() -> applyAssoc(lower, fn));
        Future<T> second = executorService.submit(() -> applyAssoc(upper, fn));

        var a = first.get();
        var b = second.get();
        return fn.apply(a, b);
    }


}
