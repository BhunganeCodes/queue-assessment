package com.assessment.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problem 5 – Hot Potato Simulation")
class HotPotatoTest {

    private HotPotato game;

    @BeforeEach
    void setUp() {
        game = new HotPotato();
    }

    // ── single-player edge case ───────────────────────────────────────────────

    @Test
    @DisplayName("With one player, that player wins immediately")
    void onePlayerWins() {
        assertEquals("Solo", game.simulate(List.of("Solo"), 3));
    }

    @Test
    @DisplayName("With two players and step 1, second player is eliminated first")
    void twoPlayerStepOne() {
        // step=1: pass once → front goes to back → front is eliminated
        // Round 1: [Alice, Bob] → pass 1 → [Bob, Alice] → eliminate Bob? 
        // Actually: pass 1 means we move Alice to back → [Bob, Alice] → eliminate Bob... 
        // Let's verify: dequeue Alice, enqueue Alice → [Bob, Alice]; dequeue Bob (eliminated)
        // Winner: Alice
        String winner = game.simulate(List.of("Alice", "Bob"), 1);
        assertEquals("Alice", winner);
    }

    // ── known outcomes ────────────────────────────────────────────────────────

    @Test
    @DisplayName("Classic example: 5 players, step 3 → Diana wins")
    void classicFivePlayersStepThree() {
        List<String> players = List.of("Alice", "Bob", "Charlie", "Diana", "Eve");
        assertEquals("Diana", game.simulate(players, 3));
    }

    @Test
    @DisplayName("6 players, step 2 → correct winner")
    void sixPlayersStepTwo() {
        List<String> players = List.of("A", "B", "C", "D", "E", "F");
        // Manual trace:
        // Round 1: pass 2 → [C,D,E,F,A,B] → eliminate C → [D,E,F,A,B]
        // Round 2: pass 2 → [F,A,B,D,E] → eliminate F → [A,B,D,E]
        // Round 3: pass 2 → [D,E,A,B] → eliminate D → [E,A,B]
        // Round 4: pass 2 → [B,E,A] → eliminate B → [E,A]
        // Round 5: pass 2 → [A,E] → eliminate A → winner: E
        assertEquals("E", game.simulate(players, 2));
    }

    @Test
    @DisplayName("step equal to player count wraps around correctly")
    void stepEqualsPlayerCount() {
        List<String> players = List.of("P1", "P2", "P3", "P4");
        // step=4 wraps once, eliminating P4 each round
        // Round 1: pass 4 → back to [P1,P2,P3,P4] → eliminate P4 → [P1,P2,P3]... 
        // Actually: starting [P1,P2,P3,P4], pass 4: P1→back, P2→back, P3→back, P4→back
        // queue is now [P1,P2,P3,P4] again → eliminate P1 → [P2,P3,P4]
        // Round 2: pass 4 on [P2,P3,P4]: P2→back,P3→back,P4→back,P2→back → [P3,P4,P2] → eliminate P3
        // Round 3: pass 4 on [P4,P2]: P4→back,P2→back,P4→back,P2→back → [P4,P2] → eliminate P4
        // Winner: P2
        assertEquals("P2", game.simulate(players, 4));
    }

    @Test
    @DisplayName("Large step value works correctly")
    void largeStepValue() {
        List<String> players = List.of("A", "B", "C");
        // step=100: equivalent to step % size at each round
        // Should not crash, should return a valid player name
        String winner = game.simulate(players, 100);
        assertTrue(List.of("A", "B", "C").contains(winner));
    }

    @Test
    @DisplayName("Winner name matches input exactly (case-sensitive)")
    void winnerNameMatchesExactly() {
        List<String> players = List.of("alice", "BOB", "Charlie");
        String winner = game.simulate(players, 2);
        assertTrue(List.of("alice", "BOB", "Charlie").contains(winner));
    }

    // ── error cases ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("null players list throws IllegalArgumentException")
    void nullPlayersThrows() {
        assertThrows(IllegalArgumentException.class, () -> game.simulate(null, 3));
    }

    @Test
    @DisplayName("Empty players list throws IllegalArgumentException")
    void emptyPlayersThrows() {
        assertThrows(IllegalArgumentException.class, () -> game.simulate(List.of(), 3));
    }

    @Test
    @DisplayName("step of 0 throws IllegalArgumentException")
    void stepZeroThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> game.simulate(List.of("Alice", "Bob"), 0));
    }

    @Test
    @DisplayName("Negative step throws IllegalArgumentException")
    void negativeStepThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> game.simulate(List.of("Alice", "Bob"), -5));
    }
}
