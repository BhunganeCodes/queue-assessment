package com.assessment.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problem 3 – Binary Number Generator")
class BinaryNumberGeneratorTest {

    private BinaryNumberGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new BinaryNumberGenerator();
    }

    // ── basic correctness ─────────────────────────────────────────────────────

    @Test
    @DisplayName("generate(1) returns [\"1\"]")
    void generateOne() {
        assertEquals(List.of("1"), generator.generate(1));
    }

    @Test
    @DisplayName("generate(3) returns [\"1\", \"10\", \"11\"]")
    void generateThree() {
        assertEquals(List.of("1", "10", "11"), generator.generate(3));
    }

    @Test
    @DisplayName("generate(7) returns the first 7 binary numbers")
    void generateSeven() {
        List<String> expected = List.of("1", "10", "11", "100", "101", "110", "111");
        assertEquals(expected, generator.generate(7));
    }

    @Test
    @DisplayName("generate(10) returns the first 10 binary numbers")
    void generateTen() {
        List<String> result = generator.generate(10);
        List<String> expected = List.of(
                "1", "10", "11", "100", "101",
                "110", "111", "1000", "1001", "1010"
        );
        assertEquals(expected, result);
    }

    // ── size guarantees ───────────────────────────────────────────────────────

    @Test
    @DisplayName("Result list has exactly n elements")
    void resultSizeEqualsN() {
        assertEquals(15, generator.generate(15).size());
    }

    // ── value correctness ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Each binary string converts back to its expected decimal value")
    void binaryValuesMatchDecimals() {
        List<String> result = generator.generate(10);
        for (int i = 0; i < result.size(); i++) {
            int decimal = Integer.parseInt(result.get(i), 2);
            assertEquals(i + 1, decimal,
                    "Binary string " + result.get(i) + " should equal decimal " + (i + 1));
        }
    }

    @Test
    @DisplayName("Result contains only '0' and '1' characters")
    void onlyBinaryCharacters() {
        List<String> result = generator.generate(20);
        for (String s : result) {
            assertTrue(s.matches("[01]+"),
                    "\"" + s + "\" contains non-binary characters");
        }
    }

    @Test
    @DisplayName("Result is in strictly ascending order")
    void ascendingOrder() {
        List<String> result = generator.generate(16);
        for (int i = 0; i < result.size() - 1; i++) {
            int current = Integer.parseInt(result.get(i), 2);
            int next    = Integer.parseInt(result.get(i + 1), 2);
            assertTrue(current < next,
                    "Expected ascending order but found " + result.get(i) + " before " + result.get(i + 1));
        }
    }

    // ── error cases ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("generate(0) throws IllegalArgumentException")
    void generateZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> generator.generate(0));
    }

    @Test
    @DisplayName("generate(-1) throws IllegalArgumentException")
    void generateNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> generator.generate(-1));
    }
}
