package com.assessment.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problem 2 – Printer Spooler")
class PrinterSpoolerTest {

    private PrinterSpooler spooler;

    @BeforeEach
    void setUp() {
        spooler = new PrinterSpooler();
    }

    // ── initial state ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("New spooler is empty")
    void newSpoolerIsEmpty() {
        assertTrue(spooler.isEmpty());
    }

    @Test
    @DisplayName("New spooler has zero pending jobs")
    void newSpoolerHasZeroPendingCount() {
        assertEquals(0, spooler.pendingCount());
    }

    @Test
    @DisplayName("New spooler has zero total pending pages")
    void newSpoolerHasZeroPages() {
        assertEquals(0, spooler.totalPendingPages());
    }

    // ── addJob ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Adding a job increases pending count")
    void addJobIncreasesPendingCount() {
        spooler.addJob("Report", 5);
        assertEquals(1, spooler.pendingCount());
        assertFalse(spooler.isEmpty());
    }

    @Test
    @DisplayName("Adding multiple jobs accumulates pending pages correctly")
    void addMultipleJobsAccumulatesPages() {
        spooler.addJob("Doc A", 3);
        spooler.addJob("Doc B", 7);
        spooler.addJob("Doc C", 10);
        assertEquals(20, spooler.totalPendingPages());
    }

    @Test
    @DisplayName("addJob throws IllegalArgumentException for null name")
    void addJobNullNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> spooler.addJob(null, 5));
    }

    @Test
    @DisplayName("addJob throws IllegalArgumentException for blank name")
    void addJobBlankNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> spooler.addJob("   ", 5));
    }

    @Test
    @DisplayName("addJob throws IllegalArgumentException for zero pages")
    void addJobZeroPagesThrows() {
        assertThrows(IllegalArgumentException.class, () -> spooler.addJob("Doc", 0));
    }

    @Test
    @DisplayName("addJob throws IllegalArgumentException for negative pages")
    void addJobNegativePagesThrows() {
        assertThrows(IllegalArgumentException.class, () -> spooler.addJob("Doc", -1));
    }

    // ── printNext ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("printNext returns the first added job (FIFO)")
    void printNextReturnsFifoOrder() {
        spooler.addJob("First", 2);
        spooler.addJob("Second", 4);
        PrinterSpooler.PrintJob job = spooler.printNext();
        assertEquals("First", job.name());
        assertEquals(2, job.pages());
    }

    @Test
    @DisplayName("printNext removes the job from the queue")
    void printNextRemovesJob() {
        spooler.addJob("Only Job", 1);
        spooler.printNext();
        assertTrue(spooler.isEmpty());
    }

    @Test
    @DisplayName("printNext returns null when queue is empty")
    void printNextReturnsNullWhenEmpty() {
        assertNull(spooler.printNext());
    }

    @Test
    @DisplayName("totalPendingPages decreases after printNext")
    void pagesDecreaseAfterPrint() {
        spooler.addJob("Big Doc", 10);
        spooler.addJob("Small Doc", 2);
        spooler.printNext();
        assertEquals(2, spooler.totalPendingPages());
    }

    // ── peekNext ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("peekNext returns the front job without removing it")
    void peekNextDoesNotRemoveJob() {
        spooler.addJob("Peek Me", 3);
        PrinterSpooler.PrintJob job = spooler.peekNext();
        assertEquals("Peek Me", job.name());
        assertEquals(1, spooler.pendingCount()); // still in queue
    }

    @Test
    @DisplayName("peekNext returns null when queue is empty")
    void peekNextReturnsNullWhenEmpty() {
        assertNull(spooler.peekNext());
    }

    // ── full workflow ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Full workflow: add three jobs and print all in FIFO order")
    void fullWorkflow() {
        spooler.addJob("Invoice", 1);
        spooler.addJob("Report", 5);
        spooler.addJob("Presentation", 12);

        assertEquals("Invoice",     spooler.printNext().name());
        assertEquals("Report",      spooler.printNext().name());
        assertEquals("Presentation",spooler.printNext().name());
        assertTrue(spooler.isEmpty());
        assertEquals(0, spooler.totalPendingPages());
    }
}
