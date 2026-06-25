package com.assessment.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * PROBLEM SET 3: Binary Number Generator
 *
 * Generate the first N binary numbers (as strings) using a Queue — not bit-shifting arithmetic.
 *
 * The queue-based algorithm is elegant:
 *  1. Start by enqueuing "1".
 *  2. Repeat N times:
 *     a. Dequeue the front element — that is the next binary number.
 *     b. Enqueue that element + "0"  (e.g. "1" → enqueue "10")
 *     c. Enqueue that element + "1"  (e.g. "1" → enqueue "11")
 *  3. Collect the N dequeued strings into a List and return it.
 *
 * Example — first 7 binary numbers:
 *   ["1", "10", "11", "100", "101", "110", "111"]
 *
 * Which map to decimals: 1, 2, 3, 4, 5, 6, 7
 *
 * Constraints:
 *  - Throw IllegalArgumentException if n < 1
 *  - Return a List<String> of exactly n elements
 *
 * Run BinaryNumberGeneratorTest to check your work.
 */
public class BinaryNumberGenerator {

    /**
     * Generate the first n binary numbers using a queue.
     *
     * @param n how many binary numbers to generate (must be >= 1)
     * @return list of binary number strings in ascending order
     * @throws IllegalArgumentException if n < 1
     */
    public List<String> generate(int n) {
        // TODO
        return null;
    }
}
