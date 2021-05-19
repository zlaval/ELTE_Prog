package com.zlrx.java.advanced.classes.cls20210421;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiFunction;

class ApplyAssoc<T> extends RecursiveTask<T> {

    private List<T> values;
    private BiFunction<T, T, T> aggregateFn;

    public ApplyAssoc(List<T> values, BiFunction<T, T, T> aggregateFn) {
        this.values = values;
        this.aggregateFn = aggregateFn;
    }

    @Override
    protected T compute() {
        if (values.size() == 1) {
            return values.get(0);
        }
        int middle = values.size() / 2;
        List<T> lower = values.subList(0, middle);
        List<T> higher = values.subList(middle, values.size());

        var first = new ApplyAssoc<T>(lower, aggregateFn);
        var second = new ApplyAssoc<T>(higher, aggregateFn);
        System.out.println(Thread.currentThread().getName() + " thread");

        first.fork();
        second.fork();

        return aggregateFn.apply(first.join(), second.join());
    }
}

public class ApplyAssocRecTask {

    public void compute() {
        ForkJoinPool fp = new ForkJoinPool();
        var task = new ApplyAssoc<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), Integer::sum);
        var result = fp.invoke(task);
        System.out.println(result);
        fp.shutdown();
    }

}
