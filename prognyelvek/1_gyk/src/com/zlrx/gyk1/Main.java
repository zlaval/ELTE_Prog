package com.zlrx.gyk1;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        var result = Arrays.stream(args)
                .map(Integer::parseInt)
                .reduce(1, (a, b) -> a * b);
        System.out.println(result);

        String.join(",",args);
        char x=1;

        short a=2;
        
    }

}
