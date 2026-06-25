package com.assessment.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problem 1 – CustomQueue")
class CustomQueueTest {

    private CustomQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new CustomQueue<>();
    }

    // ── isEmpty ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("A newly created queue is empty")
    void newQueueIsEmpty() {
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Queue is not empty after an enqueue")
    void notEmptyAfterEnqueue() {
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
    }

    @Test
    @DisplayName("Queue is empty again after enqueuing and dequeuing one element")
    void emptyAfterEnqueueAndDequeue() {
        queue.enqueue(42);
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    // ── size ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Size of a new queue is 0")
    void initialSizeIsZero() {
        assertEquals(0, queue.size());
    }

    @Test
    @DisplayName("Size increments with each enqueue")
    void sizeIncrementsOnEnqueue() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(3, queue.size());
    }

    @Test
    @DisplayName("Size decrements with each dequeue")
    void sizeDecrementsOnDequeue() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.dequeue();
        assertEquals(1, queue.size());
    }

    // ── FIFO ordering ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Elements come out in FIFO order")
    void fifoOrdering() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    // ── peek ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("peek returns the front element without removing it")
    void peekReturnsFirstWithoutRemoving() {
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(10, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    @DisplayName("peek after several enqueues still returns the original front")
    void peekReturnsOriginalFront() {
        queue.enqueue(5);
        queue.enqueue(10);
        queue.enqueue(15);
        assertEquals(5, queue.peek());
    }

    // ── exceptions ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("dequeue on an empty queue throws NoSuchElementException")
    void dequeueEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    @Test
    @DisplayName("peek on an empty queue throws NoSuchElementException")
    void peekEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    // ── dynamic resizing ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Queue handles more elements than the initial capacity (dynamic resize)")
    void dynamicResizing() {
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
        }
        assertEquals(100, queue.size());
        assertEquals(0, queue.dequeue()); // FIFO: first in is first out
        assertEquals(1, queue.dequeue());
    }

    @Test
    @DisplayName("FIFO ordering is preserved after a resize")
    void fifoAfterResize() {
        for (int i = 1; i <= 20; i++) {
            queue.enqueue(i);
        }
        for (int i = 1; i <= 20; i++) {
            assertEquals(i, queue.dequeue());
        }
    }

    // ── circular wrap-around ──────────────────────────────────────────────────

    @Test
    @DisplayName("Circular wrap-around: interleaved enqueue and dequeue maintains FIFO")
    void circularWrapAround() {
        // Fill partially, drain partially, then fill again — exercises the wrap
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        queue.enqueue(4);
        queue.enqueue(5);
        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertEquals(5, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    // ── generic type ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Queue works with String type")
    void worksWithStrings() {
        CustomQueue<String> stringQueue = new CustomQueue<>();
        stringQueue.enqueue("first");
        stringQueue.enqueue("second");
        stringQueue.enqueue("third");
        assertEquals("first", stringQueue.dequeue());
        assertEquals("second", stringQueue.dequeue());
    }
}
