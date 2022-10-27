package org.example.pr1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static void findMin(int[] array) throws InterruptedException {
        int min = Integer.MAX_VALUE;
        for (int element : array) {
            if (element < min) {
                min = element;
            }
            Thread.sleep(1);
        }
        System.out.println("min: " + min);
    }

    private static void findMinMultithreading(int[] array, int threadAmount) throws InterruptedException {
        int step = array.length / threadAmount;
        List<Finder> finderList = new ArrayList<>();
        for (int i = 0; i < threadAmount; i++) {
            Finder f;
            if (i == threadAmount - 1) {
                f = new Finder(i * step, array.length, array);
            } else {
                f = new Finder(i * step, (i + 1) * step, array);
            }
            f.start();
            finderList.add(f);
        }

        for (Finder f : finderList) {
            f.join();
        }

        int min = Integer.MAX_VALUE;
        for (var finder : finderList) {
            if (finder.getMin() < min) {
                min = finder.getMin();
            }
        }
        System.out.println("min: " + min);
    }

    private static void findMinForkJoin(int[] array) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        FindMinRecursiveTask findMinRecursiveTask = new FindMinRecursiveTask(array);
        System.out.println("min: " + forkJoinPool.invoke(findMinRecursiveTask));
    }

    private static void fillArray(int[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] array = new int[10_000];
        fillArray(array);
        long start = System.currentTimeMillis();
        findMin(array);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Последовательный поиск минимума: " + timeElapsed);

        start = System.currentTimeMillis();
        findMinMultithreading(array, 100);
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.println("Многопоточный поиск минимума: " + timeElapsed);

        start = System.currentTimeMillis();
        findMinForkJoin(array);
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.println("ForkJoin поиск минимума: " + timeElapsed);
    }
}
