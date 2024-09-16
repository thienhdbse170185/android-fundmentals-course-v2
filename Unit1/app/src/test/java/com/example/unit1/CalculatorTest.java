package com.example.unit1;

import com.example.unit1.utils.Calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {
    private Calculator mCalculator;

    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    @Test
    public void addTwoNumbers() {
        int resultAdd = mCalculator.add(1, 1);
        assertEquals(2, resultAdd);
    }

    @Test
    public void subtractTwoNumbers() {
        int resultSubtract = mCalculator.subtract(2, 1);
        assertEquals(1, resultSubtract);
    }
}
