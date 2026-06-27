package com.assessment.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * PROBLEM SET 4: Task Scheduler with Undo
 *
 * Simulate a task scheduler that supports both normal FIFO task execution
 * AND an undo feature — letting the user re-queue the most recently completed task.
 *
 * This problem introduces the DEQUE (double-ended queue):
 *   - New tasks join the BACK  (like a normal queue)
 *   - The next task to execute is taken from the FRONT  (like a normal queue)
 *   - Completed tasks are pushed onto a separate "history" Deque
 *   - Undo pops the last completed task off history and adds it to the FRONT of the task queue
 *
 * A static nested record is provided for you:
 *
 *     record Task(String name) {}
 *
 * Methods to implement:
 *  - addTask(String name)   : Add a new task to the back of the queue
 *  - executeNext()          : Execute (remove & return) the front task; move it to history
 *  - undoLast()             : Re-queue the most recently executed task at the FRONT
 *  - pendingTasks()         : Return an unmodifiable snapshot list of tasks still waiting
 *  - historySize()          : Return the number of tasks that have been executed (not undone)
 *  - hasPending()           : Return true if there are tasks waiting
 *
 * Constraints:
 *  - addTask() throws IllegalArgumentException for null/blank name
 *  - executeNext() throws NoSuchElementException if no tasks are pending
 *  - undoLast() throws NoSuchElementException if history is empty
 *  - Use java.util.Deque (ArrayDeque) for both the task queue and history
 *
 * Run TaskSchedulerTest to check your work.
 */
public class TaskScheduler {

    /** Represents a single schedulable task. */
    public record Task(String name) {}

    // TODO: declare a Deque<Task> for pending tasks
    // TODO: declare a Deque<Task> for execution history
    private Deque<Task> pendingTasks;
    private Deque<Task> history;

    public TaskScheduler() {
        // TODO: initialise both deques
        this.pendingTasks = new ArrayDeque<>();
        this.history = new ArrayDeque<>();
    }

    /**
     * Add a new task to the back of the pending queue.
     *
     * @throws IllegalArgumentException if name is null or blank
     */
    public void addTask(String name) {
        // TODO
        if (name == null || name.isBlank()) throw new IllegalArgumentException();

        Task task = new Task(name);
        pendingTasks.addFirst(task);
    }

    /**
     * Execute (dequeue from the front) the next pending task.
     * Move the executed task into the history deque.
     *
     * @return the executed Task
     * @throws NoSuchElementException if no tasks are pending
     */
    public Task executeNext() {
        // TODO
        return null;
    }

    /**
     * Undo the most recently executed task by moving it from the history
     * back to the FRONT of the pending task queue (so it executes next).
     *
     * @return the task that was re-queued
     * @throws NoSuchElementException if there is no execution history
     */
    public Task undoLast() {
        // TODO
        return null;
    }

    /**
     * Return an unmodifiable snapshot of the tasks currently waiting (in order).
     * Changes to the internal deque must NOT affect the returned list.
     */
    public List<Task> pendingTasks() {
        // TODO
        return null;
    }

    /** Return the number of tasks in the execution history. */
    public int historySize() {
        // TODO
        return 0;
    }

    /** Return true if there are tasks waiting to be executed. */
    public boolean hasPending() {
        // TODO
        return false;
    }
}
