package com.assessment.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * PROBLEM SET 2: Printer Spooler
 *
 * Simulate a printer spooler — a real-world system that queues print jobs
 * and processes them in the order they arrive (FIFO).
 *
 * You will manage a queue of print jobs. Each job has a name and a page count.
 * The spooler can accept new jobs and process (print) the next one in line.
 *
 * A static nested record is provided for you:
 *
 *     record PrintJob(String name, int pages) {}
 *
 * Methods to implement:
 *  - addJob(String name, int pages)  : Add a new print job to the back of the queue
 *  - printNext()                     : Remove and return the next job; null if queue is empty
 *  - peekNext()                      : Return the next job without removing it; null if empty
 *  - pendingCount()                  : Return the number of jobs waiting
 *  - totalPendingPages()             : Return the sum of pages across all pending jobs
 *  - isEmpty()                       : Return true if no jobs are queued
 *
 * Constraints:
 *  - addJob() must throw IllegalArgumentException if name is null/blank or pages < 1
 *  - You MAY use java.util.Queue / LinkedList here (this problem tests design, not internals)
 *
 * Run PrinterSpoolerTest to check your work.
 */
public class PrinterSpooler {

    /** Represents a single print job. */
    public record PrintJob(String name, int pages) {}

    // TODO: declare a Queue<PrintJob> field
    private Queue<PrintJob> printJobs;
    private int pages;

    public PrinterSpooler() {
        // TODO: initialise the queue
        this.printJobs = new LinkedList<PrintJob>();
        this.pages = 0;
    }

    /**
     * Add a new print job to the back of the queue.
     *
     * @throws IllegalArgumentException if name is null/blank or pages < 1
     */
    public void addJob(String name, int pages) {
        // TODO
        if (name == null || name.isBlank() || pages < 1) throw new IllegalArgumentException();
        printJobs.offer(new PrintJob(name, pages));
        this.pages += pages;
    }

    /**
     * Remove and return the next job from the front of the queue.
     * Returns null if the queue is empty.
     */
    public PrintJob printNext() {
        // TODO
        if (printJobs.isEmpty()) return null;
        pages-= printJobs.element().pages();
        return printJobs.poll();
    }

    /**
     * Return the next job without removing it.
     * Returns null if the queue is empty.
     */
    public PrintJob peekNext() {
        // TODO
        if (printJobs.isEmpty()) return null;
        return printJobs.element();
    }

    /** Return the number of jobs currently waiting. */
    public int pendingCount() {
        // TODO
        return printJobs.size();
    }

    /** Return the total number of pages across all pending jobs. */
    public int totalPendingPages() {
        // TODO
        return printNext().pages();
    }

    /** Return true if there are no pending jobs. */
    public boolean isEmpty() {
        // TODO
        return printJobs.isEmpty();
    }
}
