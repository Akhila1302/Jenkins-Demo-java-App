package com.example.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorApplicationTest {

    @Test
    void testAddition() {
        assertEquals(30, 10 + 20);
    }

    @Test
    void testSubtraction() {
        assertEquals(10, 20 - 10);
    }
}