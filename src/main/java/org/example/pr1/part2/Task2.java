package org.example.pr1.part2;

import java.util.Scanner;
import java.util.concurrent.FutureTask;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите любое число и оно будет возведено в квадрат. Введите 0, чтобы закрыть программу.");

        int number;
        do {
            number = scanner.nextInt();

            FutureTask<Integer> task = new FutureTask<>(new Square(number));
            new Thread(task).start();
        } while (number != 0);
    }
}

