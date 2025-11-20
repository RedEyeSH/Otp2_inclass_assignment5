package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many numbers to display in the loop? ");
        int count = scanner.nextInt();

        displayNumbers(count);
    }

    public static void displayNumbers(int count) {
        for (int i = 1; i <= count; i++) {
            System.out.println("i = " + i);
        }
    }
}