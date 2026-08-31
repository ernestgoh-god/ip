# Neo UI Test Plan

## Test setup

Run all commands from the repository root. The build command uses Java 25 and
the program command starts a new Neo session for each test case.

### Build command

```text
javac -d out src/main/java/neo/Neo.java src/main/java/neo/Task.java src/main/java/neo/Todo.java src/main/java/neo/Deadline.java src/main/java/neo/Event.java
```

### Program command

```text
java -cp out neo.Neo
```

## Test case: Exit the application

### Aim

Verify that the `bye` command ends a new Neo session with a farewell message.

### Inputs

```text
bye
```

### Expected output

```text
    ____________________________________________________________
 _   _
| \ | | ___  ___
|  \| |/ _ \/ _ \
| |\  |  __/ (_) |
|_| \_|\___|\___/
     Hello! I'm Neo.
     What can I do for you?
    ____________________________________________________________

    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test case: Add and list a todo task

### Aim

Verify that Neo stores a todo task and displays it in the task list.

### Inputs

```text
todo borrow book
list
bye
```

### Expected output

```text
    ____________________________________________________________
 _   _
| \ | | ___  ___
|  \| |/ _ \/ _ \
| |\  |  __/ (_) |
|_| \_|\___|\___/
     Hello! I'm Neo.
     What can I do for you?
    ____________________________________________________________

    ____________________________________________________________
     Got it. I've added this task:
     [T][ ] borrow book
     Now you have 1 tasks in the list.
    ____________________________________________________________

    ____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] borrow book
    ____________________________________________________________

    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test case: Manage deadline and event tasks

### Aim

Verify that Neo creates deadline and event tasks, changes task completion,
and preserves the resulting task states in the list.

### Inputs

```text
deadline submit report /by Monday
event team sync /from 10am /to 11am
mark 2
unmark 2
list
bye
```

### Expected output

```text
    ____________________________________________________________
 _   _
| \ | | ___  ___
|  \| |/ _ \/ _ \
| |\  |  __/ (_) |
|_| \_|\___|\___/
     Hello! I'm Neo.
     What can I do for you?
    ____________________________________________________________

    ____________________________________________________________
     Got it. I've added this task:
     [D][ ] submit report (by: Monday)
     Now you have 1 tasks in the list.
    ____________________________________________________________

    ____________________________________________________________
     Got it. I've added this task:
     [E][ ] team sync (from: 10am) (to: 11am)
     Now you have 2 tasks in the list.
    ____________________________________________________________

    ____________________________________________________________
     Nice! I've marked this task as done:
       [E][X] team sync (from: 10am) (to: 11am)
    ____________________________________________________________

    ____________________________________________________________
     OK, I've marked this task as not done yet:
       [E][ ] team sync (from: 10am) (to: 11am)
    ____________________________________________________________

    ____________________________________________________________
     Here are the tasks in your list:
     1.[D][ ] submit report (by: Monday)
     2.[E][ ] team sync (from: 10am) (to: 11am)
    ____________________________________________________________

    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Latest test session

Run started: 2026-08-31T19:12:51+08:00

### Exit the application

**Result:** PASS

**Aim:** Verify that the `bye` command ends a new Neo session with a farewell message.

#### Console input
```text
bye
```

#### Console output
```text
    ____________________________________________________________
 _   _
| \ | | ___  ___
|  \| |/ _ \/ _ \
| |\  |  __/ (_) |
|_| \_|\___|\___/
     Hello! I'm Neo.
     What can I do for you?
    ____________________________________________________________

    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

### Add and list a todo task

**Result:** PASS

**Aim:** Verify that Neo stores a todo task and displays it in the task list.

#### Console input
```text
todo borrow book
list
bye
```

#### Console output
```text
    ____________________________________________________________
 _   _
| \ | | ___  ___
|  \| |/ _ \/ _ \
| |\  |  __/ (_) |
|_| \_|\___|\___/
     Hello! I'm Neo.
     What can I do for you?
    ____________________________________________________________

    ____________________________________________________________
     Got it. I've added this task:
     [T][ ] borrow book
     Now you have 1 tasks in the list.
    ____________________________________________________________

    ____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] borrow book
    ____________________________________________________________

    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

### Manage deadline and event tasks

**Result:** PASS

**Aim:** Verify that Neo creates deadline and event tasks, changes task completion,
and preserves the resulting task states in the list.

#### Console input
```text
deadline submit report /by Monday
event team sync /from 10am /to 11am
mark 2
unmark 2
list
bye
```

#### Console output
```text
    ____________________________________________________________
 _   _
| \ | | ___  ___
|  \| |/ _ \/ _ \
| |\  |  __/ (_) |
|_| \_|\___|\___/
     Hello! I'm Neo.
     What can I do for you?
    ____________________________________________________________

    ____________________________________________________________
     Got it. I've added this task:
     [D][ ] submit report (by: Monday)
     Now you have 1 tasks in the list.
    ____________________________________________________________

    ____________________________________________________________
     Got it. I've added this task:
     [E][ ] team sync (from: 10am) (to: 11am)
     Now you have 2 tasks in the list.
    ____________________________________________________________

    ____________________________________________________________
     Nice! I've marked this task as done:
       [E][X] team sync (from: 10am) (to: 11am)
    ____________________________________________________________

    ____________________________________________________________
     OK, I've marked this task as not done yet:
       [E][ ] team sync (from: 10am) (to: 11am)
    ____________________________________________________________

    ____________________________________________________________
     Here are the tasks in your list:
     1.[D][ ] submit report (by: Monday)
     2.[E][ ] team sync (from: 10am) (to: 11am)
    ____________________________________________________________

    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```
