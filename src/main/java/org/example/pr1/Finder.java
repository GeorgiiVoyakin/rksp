package org.example.pr1;

public class Finder extends Thread {
    private final int start;
    private final int stop;
    private int min;
    private final int[] array;

    public Finder(int start, int stop, int[] array) {
        this.start = start;
        this.stop = stop;
        this.array = array;
        this.min = Integer.MAX_VALUE;
    }

    @Override
    public void run() {
        for (int i = start; i < stop; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getMin() {
        return min;
    }
}
