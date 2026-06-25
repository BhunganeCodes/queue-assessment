# Queue Data Structure — TDD Assessment

Welcome back! This is the Queue companion to the Stack assessment.
You will work through **5 problem sets** that cover the Queue in increasing depth —
from building one by hand to using it to solve real problems.

| # | Source file | Test file | Topic |
|---|-------------|-----------|-------|
| 1 | `CustomQueue.java` | `CustomQueueTest.java` | Build a queue from scratch (circular array) |
| 2 | `PrinterSpooler.java` | `PrinterSpoolerTest.java` | Real-world FIFO modelling |
| 3 | `BinaryNumberGenerator.java` | `BinaryNumberGeneratorTest.java` | Queue-based number generation |
| 4 | `TaskScheduler.java` | `TaskSchedulerTest.java` | Deque with undo support |
| 5 | `HotPotato.java` | `HotPotatoTest.java` | Queue rotation simulation |

---

## Getting Started

### Prerequisites
- **Java 25** (JDK 25 installed and set as the project SDK in IntelliJ)
- **Maven 3.9+**
- **IntelliJ IDEA** (Community or Ultimate)

### Opening the Project
1. Extract the zip to a folder of your choice.
2. Open **IntelliJ IDEA → File → Open** and select the `queue-assessment` folder.
3. IntelliJ detects `pom.xml` and imports the Maven project automatically.
4. Go to **File → Project Structure → Project** and confirm the SDK is set to **JDK 25**.
5. Wait for the Maven sync to complete.

### Running the Tests
- **All tests:** Right-click `src/test/java` → *Run All Tests*
- **One problem:** Open the test file → click ▶ next to the class name
- **Terminal:** `mvn test`

All tests start **red** — that is expected. Make them green one by one.

---

## Queue vs Stack — Key Difference

| | Stack | Queue |
|-|-------|-------|
| Ordering | LIFO — Last In, First Out | FIFO — First In, First Out |
| Real-world analogy | Stack of plates | Queue at a checkout |
| Add | `push` → top | `enqueue` → back |
| Remove | `pop` ← top | `dequeue` ← front |

---

## How TDD Works

```
RED    → Read the failing test — understand what it expects
GREEN  → Write the minimum code to make it pass
REFACTOR → Clean up without breaking tests
```

Work problem by problem. Each one builds intuition for the next.

---

## Problem Set 1 — Build Your Own Queue (`CustomQueue.java`)

**Difficulty:** ⭐⭐☆  
**Concepts:** Circular arrays, head/tail pointers, dynamic resizing, generics

### Background
A Queue stores elements in FIFO order. The challenge of an array-backed queue is
that naively shifting elements left on every dequeue is O(n). The professional
solution is a **circular array** — head and tail pointers that wrap around the
array using the modulo operator.

### Methods
| Method | Description |
|--------|-------------|
| `enqueue(T item)` | Add item to the back |
| `dequeue()` | Remove and return the front item |
| `peek()` | Return the front item without removing it |
| `isEmpty()` | `true` if no elements |
| `size()` | Number of elements |

### Circular Array — How It Works
```
Initial state (capacity 4):
  [ _, _, _, _ ]
    ^
   head=0, tail=0

After enqueue(A), enqueue(B), enqueue(C):
  [ A, B, C, _ ]
    ^        ^
  head=0   tail=3

After dequeue() → returns A:
  [ _, B, C, _ ]
       ^     ^
     head=1  tail=3

After enqueue(D), enqueue(E) — tail wraps:
  [ E, B, C, D ]
       ^  ^
     head=1  tail=1  ← wrapped around!
```

### Tips
- Use `Object[]` as the backing store (same trick as CustomStack).
- Advance pointers with modulo: `tail = (tail + 1) % capacity`
- The queue is **full** when `(tail + 1) % capacity == head`
- On resize: copy elements in logical order starting from `head`, then reset `head = 0`, `tail = size`
- `dequeue()` and `peek()` throw `NoSuchElementException` when empty

---

## Problem Set 2 — Printer Spooler (`PrinterSpooler.java`)

**Difficulty:** ⭐☆☆  
**Concepts:** Queue design, validation, records

### Background
A printer spooler is a classic queue application — print jobs are queued up and
processed in the order they arrive. You are given a `PrintJob` record (Java 25 record):

```java
public record PrintJob(String name, int pages) {}
```

Records are immutable data carriers. Access fields with `job.name()` and `job.pages()`.

### Methods
| Method | Description |
|--------|-------------|
| `addJob(String name, int pages)` | Add a job; throws `IllegalArgumentException` for invalid input |
| `printNext()` | Remove and return the next job; `null` if empty |
| `peekNext()` | Return the next job without removing; `null` if empty |
| `pendingCount()` | Number of waiting jobs |
| `totalPendingPages()` | Sum of pages across all waiting jobs |
| `isEmpty()` | `true` if no jobs are queued |

### Validation Rules
- `name` must not be `null` or blank (use `String.isBlank()`)
- `pages` must be ≥ 1

### Tip
You **may** use `java.util.LinkedList` or `java.util.ArrayDeque` as your backing queue here.
This problem tests how you model a real system, not internals.

---

## Problem Set 3 — Binary Number Generator (`BinaryNumberGenerator.java`)

**Difficulty:** ⭐⭐☆  
**Concepts:** Queue-based generation, string manipulation, BFS thinking

### Background
This problem shows the queue's power as a **generation** tool.
Instead of using arithmetic bit-shifts, you grow binary numbers organically
by appending `"0"` and `"1"` to each number you dequeue.

### Algorithm (read this carefully!)

```
Start: enqueue "1"

Iteration 1: dequeue "1"  → result gets "1"
             enqueue "10", enqueue "11"
             Queue: ["10", "11"]

Iteration 2: dequeue "10" → result gets "10"
             enqueue "100", enqueue "101"
             Queue: ["11", "100", "101"]

Iteration 3: dequeue "11" → result gets "11"
             enqueue "110", enqueue "111"
             Queue: ["100", "101", "110", "111"]

... and so on for N iterations.
```

Result for N=7: `["1", "10", "11", "100", "101", "110", "111"]`

### Tip
Each string you dequeue represents a binary number. Appending `"0"` doubles it;
appending `"1"` doubles it and adds one. This is essentially Breadth-First
traversal of a binary tree of numbers — a pattern you will see again in graph algorithms.

---

## Problem Set 4 — Task Scheduler with Undo (`TaskScheduler.java`)

**Difficulty:** ⭐⭐☆  
**Concepts:** Deque (double-ended queue), history tracking, snapshot lists

### Background
This problem introduces the **Deque** (`java.util.ArrayDeque`) — a data structure
that supports insertion and removal from **both ends**.

```
Normal Queue:   add at BACK,    remove from FRONT
Deque extra:    add at FRONT,   remove from BACK  ← used for undo
```

You will maintain two deques:

```
pendingTasks  [Alpha, Beta, Gamma]  ← Alpha executes next
               front              back

history       [Alpha]              ← most recently executed at the back
               front              back
```

### Undo Logic
When `undoLast()` is called:
- Pop the **last** element from `history` (use `removeLast()`)
- Push it to the **front** of `pendingTasks` (use `addFirst()`) so it runs next

### Key `ArrayDeque` Methods
| Method | Description |
|--------|-------------|
| `addLast(e)` | Add to back (normal enqueue) |
| `addFirst(e)` | Add to front |
| `removeFirst()` | Remove from front (normal dequeue) |
| `removeLast()` | Remove from back |
| `peekFirst()` | Peek at front |

### Snapshot Requirement
`pendingTasks()` must return an **unmodifiable copy** — changes to the scheduler
after calling it must not affect the returned list.
Use `List.copyOf(deque)` to achieve this.

---

## Problem Set 5 — Hot Potato (`HotPotato.java`)

**Difficulty:** ⭐⭐⭐  
**Concepts:** Queue rotation, simulation, modular reasoning

### Background
Hot Potato is a classic queue simulation. Players form a circle; after a fixed
number of passes the player holding the potato is eliminated.

### Algorithm

```
players = [Alice, Bob, Charlie, Diana, Eve], step = 3

Start queue: front → [Alice, Bob, Charlie, Diana, Eve] ← back

Round 1 — pass 3 times:
  Pass 1: dequeue Alice,   enqueue Alice   → [Bob, Charlie, Diana, Eve, Alice]
  Pass 2: dequeue Bob,     enqueue Bob     → [Charlie, Diana, Eve, Alice, Bob]
  Pass 3: dequeue Charlie, enqueue Charlie → [Diana, Eve, Alice, Bob, Charlie]
  Eliminate front: dequeue Charlie (out!)  → [Diana, Eve, Alice, Bob]  ✗ Charlie

Round 2 — pass 3 times from [Diana, Eve, Alice, Bob]:
  ...eliminate Alice → [Bob, Diana, Eve]

Round 3 — eliminate Eve → [Bob, Diana]

Round 4 — eliminate Bob → [Diana]

Winner: Diana 🏆
```

### Tips
- Load players into a `Queue` with `addAll()`.
- The inner loop runs exactly `step` times per round: dequeue + enqueue.
- After the inner loop, dequeue once more (no re-enqueue) to eliminate.
- The game ends when `queue.size() == 1`.

---

## Checking Your Progress

```bash
mvn test
```

Aim for all green before moving to the next problem.

```
[INFO] Tests run: 13, Failures: 0  -- CustomQueueTest
[INFO] Tests run: 12, Failures: 0  -- PrinterSpoolerTest
[INFO] Tests run: 10, Failures: 0  -- BinaryNumberGeneratorTest
[INFO] Tests run: 12, Failures: 0  -- TaskSchedulerTest
[INFO] Tests run: 10, Failures: 0  -- HotPotatoTest
```

---

## Suggested Order

```
Problem 1 → Problem 2 → Problem 3 → Problem 4 → Problem 5
```

Good luck! 🚀
