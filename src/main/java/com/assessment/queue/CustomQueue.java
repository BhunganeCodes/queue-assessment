package com.assessment.queue;

import java.util.NoSuchElementException;

/**
 * PROBLEM SET 1: Build Your Own Queue
 *
 * Your task is to implement a generic queue from scratch using an array internally.
 * A Queue follows FIFO (First In, First Out) ordering — like a checkout line at a shop.
 *
 * You must NOT use java.util.Queue, java.util.Deque, java.util.LinkedList, or any other
 * collection class. Only a plain Object array is allowed as the backing store.
 *
 * Methods to implement:
 *  - enqueue(T item)  : Add an item to the back of the queue
 *  - dequeue()        : Remove and return the item at the front
 *  - peek()           : Return the front item without removing it
 *  - isEmpty()        : Return true if the queue has no elements
 *  - size()           : Return the number of elements in the queue
 *
 * Constraints:
 *  - Use a CIRCULAR array design (head and tail pointers that wrap around)
 *  - The queue must grow dynamically (double capacity when full)
 *  - dequeue() and peek() on an empty queue must throw NoSuchElementException
 *
 * Hint — circular array:
 *  - Keep two int pointers: `head` (front) and `tail` (next empty slot).
 *  - After each enqueue, advance tail: tail = (tail + 1) % capacity
 *  - After each dequeue, advance head: head = (head + 1) % capacity
 *  - The queue is full when (tail + 1) % capacity == head
 *
 * Run CustomQueueTest to check your work.
 */
public class CustomQueue<T> {

    private Object[] backingArray;
    private int head;
    private int tail;
    private int size;
    private int capacity;

    public CustomQueue() {
        // TODO: initialise your array with a starting capacity (e.g. 4)
        //       set head, tail, and size to 0
        this.capacity = 4;
        this.backingArray = new Object[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    /** Add an item to the back of the queue. */
    public void enqueue(T item) {
        // TODO: if full, resize first
        //       place item at backingArray[tail]
        //       advance tail circularly: tail = (tail + 1) % capacity
        //       increment size
        if (size == capacity) {
            Object[] newArray = new Object[capacity * 2];
            backingArray = newArray;
        }
        backingArray[tail] = item;
        tail = (tail + 1) % capacity;
        size++;
    }

    /** Remove and return the front item. Throws NoSuchElementException if empty. */
    public T dequeue() {
        // TODO: throw NoSuchElementException if empty
        //       grab item at backingArray[head]
        //       null out that slot (helps GC)
        //       advance head circularly: head = (head + 1) % capacity
        //       decrement size and return the item
        return null;
    }

    /** Return the front item without removing it. Throws NoSuchElementException if empty. */
    public T peek() {
        // TODO
        return null;
    }

    /** Return true if the queue contains no elements. */
    public boolean isEmpty() {
        // TODO
        return true;
    }

    /** Return the number of elements currently in the queue. */
    public int size() {
        // TODO
        return 0;
    }

    /**
     * Helper — resize the backing array when it is full.
     * Copy elements in logical order (head → tail) into a new array of double the capacity.
     * Reset head to 0 and tail to the old size.
     */
    private void resize() {
        // TODO
    }
}
