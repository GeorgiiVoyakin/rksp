package org.example.pr1;

public class NewFinder extends Thread {
    private int min;
    private final int[] array;

    public NewFinder(int[] array) {

        this.array = array;
        this.min = Integer.MAX_VALUE;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length; i++) {
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
