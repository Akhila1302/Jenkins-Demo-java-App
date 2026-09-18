package com.example.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorApplicationTest {

    @Test
    void testAddition() {

        int result = CalculatorApplication.add(10, 20);

        assertEquals(30, result);
    }

    @Test
    void testSubtraction() {

        int result = CalculatorApplication.subtract(20, 10);

        assertEquals(10, result);
    }
}