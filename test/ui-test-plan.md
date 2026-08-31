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

## Latest test session

Run started: 2026-08-31T18:54:48+08:00

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
