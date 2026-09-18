package com.example.app;

public class CalculatorApplication {

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) {

        System.out.println("Jenkins CI/CD Demo Application");

        int result = add(10, 20);

        System.out.println("10 + 20 = " + result);
    }
}