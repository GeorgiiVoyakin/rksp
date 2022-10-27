package org.example.pr1.part2;

import java.util.concurrent.Callable;

class Square implements Callable<Integer> {

    private final int number;

    public Square(int number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(3000);
        Integer square = number * number;
        System.out.println("Result: " + square);

        return square;
    }
}
