package com.assessment.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problem 4 – Task Scheduler with Undo")
class TaskSchedulerTest {

    private TaskScheduler scheduler;

    @BeforeEach
    void setUp() {
        scheduler = new TaskScheduler();
    }

    // ── initial state ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("New scheduler has no pending tasks")
    void newSchedulerHasNoPending() {
        assertFalse(scheduler.hasPending());
    }

    @Test
    @DisplayName("New scheduler has empty history")
    void newSchedulerHasEmptyHistory() {
        assertEquals(0, scheduler.historySize());
    }

    // ── addTask ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("addTask makes hasPending return true")
    void addTaskCreatesPending() {
        scheduler.addTask("Build");
        assertTrue(scheduler.hasPending());
    }

    @Test
    @DisplayName("addTask throws for null name")
    void addTaskNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> scheduler.addTask(null));
    }

    @Test
    @DisplayName("addTask throws for blank name")
    void addTaskBlankThrows() {
        assertThrows(IllegalArgumentException.class, () -> scheduler.addTask("  "));
    }

    @Test
    @DisplayName("pendingTasks returns tasks in insertion order")
    void pendingTasksInOrder() {
        scheduler.addTask("Alpha");
        scheduler.addTask("Beta");
        scheduler.addTask("Gamma");
        var names = scheduler.pendingTasks().stream()
                .map(TaskScheduler.Task::name).toList();
        assertEquals(java.util.List.of("Alpha", "Beta", "Gamma"), names);
    }

    // ── executeNext ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("executeNext returns the first added task (FIFO)")
    void executeNextFifo() {
        scheduler.addTask("First");
        scheduler.addTask("Second");
        assertEquals("First", scheduler.executeNext().name());
    }

    @Test
    @DisplayName("executeNext moves the task to history")
    void executeNextMovesToHistory() {
        scheduler.addTask("Deploy");
        scheduler.executeNext();
        assertEquals(1, scheduler.historySize());
        assertFalse(scheduler.hasPending());
    }

    @Test
    @DisplayName("executeNext throws NoSuchElementException when no pending tasks")
    void executeNextEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> scheduler.executeNext());
    }

    // ── undoLast ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("undoLast re-queues the last executed task")
    void undoLastRequeuesTask() {
        scheduler.addTask("Task A");
        scheduler.addTask("Task B");
        scheduler.executeNext(); // executes "Task A"
        scheduler.undoLast();   // puts "Task A" back at the front

        assertEquals("Task A", scheduler.executeNext().name());
    }

    @Test
    @DisplayName("undoLast places the task at the FRONT of the queue")
    void undoLastPlacesAtFront() {
        scheduler.addTask("First");
        scheduler.addTask("Second");
        scheduler.executeNext();        // executes "First"
        scheduler.undoLast();           // "First" goes back to front

        var names = scheduler.pendingTasks().stream()
                .map(TaskScheduler.Task::name).toList();
        assertEquals("First", names.get(0));
        assertEquals("Second", names.get(1));
    }

    @Test
    @DisplayName("undoLast decrements history size")
    void undoLastDecrementsHistory() {
        scheduler.addTask("X");
        scheduler.executeNext();
        assertEquals(1, scheduler.historySize());
        scheduler.undoLast();
        assertEquals(0, scheduler.historySize());
    }

    @Test
    @DisplayName("undoLast throws NoSuchElementException when history is empty")
    void undoLastEmptyHistoryThrows() {
        assertThrows(NoSuchElementException.class, () -> scheduler.undoLast());
    }

    // ── pendingTasks snapshot ─────────────────────────────────────────────────

    @Test
    @DisplayName("pendingTasks returns an unmodifiable list")
    void pendingTasksIsUnmodifiable() {
        scheduler.addTask("A");
        var snapshot = scheduler.pendingTasks();
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(new TaskScheduler.Task("B")));
    }

    @Test
    @DisplayName("pendingTasks snapshot does not reflect subsequent changes")
    void pendingTasksSnapshotIsStable() {
        scheduler.addTask("Alpha");
        var snapshot = scheduler.pendingTasks();
        scheduler.addTask("Beta");
        assertEquals(1, snapshot.size()); // snapshot captured before Beta was added
    }

    // ── full workflow ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Full workflow: add, execute, undo, re-execute")
    void fullWorkflow() {
        scheduler.addTask("Compile");
        scheduler.addTask("Test");
        scheduler.addTask("Deploy");

        assertEquals("Compile", scheduler.executeNext().name());
        assertEquals("Test",    scheduler.executeNext().name());
        assertEquals(2, scheduler.historySize());

        scheduler.undoLast(); // "Test" returns to the front
        assertEquals(1, scheduler.historySize());

        assertEquals("Test",   scheduler.executeNext().name());
        assertEquals("Deploy", scheduler.executeNext().name());
        assertFalse(scheduler.hasPending());
        assertEquals(3, scheduler.historySize());
    }
}
