package com.assessment.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * PROBLEM SET 5: Hot Potato Simulation
 *
 * Simulate the children's game "Hot Potato":
 *  - N players stand in a circle.
 *  - Each round, the potato is passed around `step` times.
 *  - The player holding the potato after `step` passes is eliminated.
 *  - The game continues until one player remains — they are the winner.
 *
 * This is a classic queue rotation problem:
 *  - Model the circle as a Queue.
 *  - Each "pass" = dequeue from the front and enqueue at the back.
 *  - After `step` passes, dequeue the front player (they are out).
 *  - Repeat until one player remains.
 *
 * Methods to implement:
 *  - simulate(List<String> players, int step) : Run the game and return the winner's name
 *
 * Example:
 *   players = ["Alice", "Bob", "Charlie", "Diana", "Eve"], step = 3
 *
 *   Round 1: pass 3 times → Charlie is eliminated  → queue: [Diana, Eve, Alice, Bob]
 *   Round 2: pass 3 times → Alice is eliminated    → queue: [Bob, Diana, Eve]
 *   Round 3: pass 3 times → Eve is eliminated      → queue: [Bob, Diana]
 *   Round 4: pass 3 times → Bob is eliminated      → winner: Diana
 *
 * Constraints:
 *  - Throw IllegalArgumentException if players is null/empty or step < 1
 *  - Player names are case-sensitive
 *  - The returned name must exactly match the input name
 *
 * Run HotPotatoTest to check your work.
 */
public class HotPotato {

    /**
     * Simulate the Hot Potato game and return the winner's name.
     *
     * @param players list of player names (order defines the starting circle)
     * @param step    number of passes per round before elimination
     * @return the name of the last remaining player
     * @throws IllegalArgumentException if players is null/empty or step < 1
     */
    public String simulate(List<String> players, int step) {
        // TODO: validate inputs
        // TODO: load all players into a Queue (preserving order)
        // TODO: while more than one player remains:
        //         pass the potato `step` times (dequeue → enqueue)
        //         eliminate the front player (dequeue, don't re-enqueue)
        // TODO: the final remaining element is the winner — return their name
        return null;
    }
}
