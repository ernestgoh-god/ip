# Neo UI Test Plan

## Test setup

Run all commands from the repository root. The build command uses Java 25 and
the program command starts a new Neo session for each test case. Each session
uses a temporary working directory under `out` with an empty `data/neo.txt`,
so tests start with no tasks and do not change the application's saved data.

### Build command

```text
javac -d out -sourcepath src/main/java src/main/java/neo/Neo.java
```

### Program command

```text
python -c "from pathlib import Path; from tempfile import TemporaryDirectory; import subprocess, sys; classes = Path('out').resolve(); session = TemporaryDirectory(prefix='neo-ui-', dir=classes); data = Path(session.name) / 'data'; data.mkdir(); (data / 'neo.txt').write_text('', encoding='utf-8'); result = subprocess.run(['java', '-cp', str(classes), 'neo.Neo'], cwd=session.name); session.cleanup(); sys.exit(result.returncode)"
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

## Test case: Reject invalid commands without corrupting task state

### Aim

Verify that invalid commands report errors without changing stored tasks,
while valid commands before and after the errors update task state correctly.

### Inputs

```text
todo alpha
mark 2
list
todo
deadline report /by Friday
event planning /from 9am
mark 1
unmark 3
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
     [T][ ] alpha
     Now you have 1 tasks in the list.
    ____________________________________________________________

    ____________________________________________________________
     Error: The task index is out of bounds.
    ____________________________________________________________

    ____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] alpha
    ____________________________________________________________

    ____________________________________________________________
     Error: Unrecognized command. Try typing: todo <description>, deadline <description> <date>, event <description> <date>, list, mark <index>, unmark <index>, or bye.
    ____________________________________________________________

    ____________________________________________________________
     Got it. I've added this task:
     [D][ ] report (by: Friday)
     Now you have 2 tasks in the list.
    ____________________________________________________________

    ____________________________________________________________
     Error: The event end date is missing. Try typing: event <description> /from <start date> /to <end date>
    ____________________________________________________________

    ____________________________________________________________
     Nice! I've marked this task as done:
       [T][X] alpha
    ____________________________________________________________

    ____________________________________________________________
     Error: The task index is out of bounds.
    ____________________________________________________________

    ____________________________________________________________
     Here are the tasks in your list:
     1.[T][X] alpha
     2.[D][ ] report (by: Friday)
    ____________________________________________________________

    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Latest test session

Run started: 2026-09-28T20:54:06+08:00

### Build command

**Result:** FAIL

#### Console output
```text
src\main\java\neo\Neo.java:4: error: package neo.exception does not exist
import neo.exception.NeoException;
                    ^
src\main\java\neo\Neo.java:5: error: package neo.task does not exist
import neo.task.Task;
               ^
src\main\java\neo\Neo.java:13: error: cannot find symbol
    private Storage storage;
            ^
  symbol:   class Storage
  location: class Neo
src\main\java\neo\Neo.java:14: error: cannot find symbol
    private TaskList tasks;
            ^
  symbol:   class TaskList
  location: class Neo
src\main\java\neo\Neo.java:15: error: cannot find symbol
    private Ui ui;
            ^
  symbol:   class Ui
  location: class Neo
src\main\java\neo\Neo.java:60: error: cannot find symbol
    private boolean processCommand(String command) throws NeoException {
                                                          ^
  symbol:   class NeoException
  location: class Neo
src\main\java\neo\Neo.java:130: error: cannot find symbol
    private void addTask(Task task) {
                         ^
  symbol:   class Task
  location: class Neo
src\main\java\neo\Neo.java:24: error: cannot find symbol
        ui = new Ui();
                 ^
  symbol:   class Ui
  location: class Neo
src\main\java\neo\Neo.java:25: error: cannot find symbol
        storage = new Storage(filePath);
                      ^
  symbol:   class Storage
  location: class Neo
src\main\java\neo\Neo.java:27: error: cannot find symbol
            tasks = new TaskList(storage.load());
                        ^
  symbol:   class TaskList
  location: class Neo
src\main\java\neo\Neo.java:28: error: cannot find symbol
        } catch (NeoException e) {
                 ^
  symbol:   class NeoException
  location: class Neo
src\main\java\neo\Neo.java:30: error: cannot find symbol
            tasks = new TaskList();
                        ^
  symbol:   class TaskList
  location: class Neo
src\main\java\neo\Neo.java:47: error: cannot find symbol
            } catch (NeoException e) {
                     ^
  symbol:   class NeoException
  location: class Neo
src\main\java\neo\Neo.java:61: error: cannot find symbol
        if (command.equals(Parser.EXIT_COMMAND)) {
                           ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:66: error: cannot find symbol
        if (command.equals(Parser.LIST_COMMAND)) {
                           ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:71: error: cannot find symbol
        if (command.startsWith(Parser.MARK_PREFIX)) {
                               ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:72: error: cannot find symbol
            int index = Parser.parseIndex(command, Parser.MARK_PREFIX, tasks.getSize());
                                                   ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:72: error: cannot find symbol
            int index = Parser.parseIndex(command, Parser.MARK_PREFIX, tasks.getSize());
                        ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:73: error: cannot find symbol
            Task t = tasks.getTask(index);
            ^
  symbol:   class Task
  location: class Neo
src\main\java\neo\Neo.java:80: error: cannot find symbol
        if (command.startsWith(Parser.UNMARK_PREFIX)) {
                               ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:81: error: cannot find symbol
            int index = Parser.parseIndex(command, Parser.UNMARK_PREFIX, tasks.getSize());
                                                   ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:81: error: cannot find symbol
            int index = Parser.parseIndex(command, Parser.UNMARK_PREFIX, tasks.getSize());
                        ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:82: error: cannot find symbol
            Task t = tasks.getTask(index);
            ^
  symbol:   class Task
  location: class Neo
src\main\java\neo\Neo.java:89: error: cannot find symbol
        if (command.startsWith(Parser.DELETE_PREFIX)) {
                               ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:90: error: cannot find symbol
            int index = Parser.parseIndex(command, Parser.DELETE_PREFIX, tasks.getSize());
                                                   ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:90: error: cannot find symbol
            int index = Parser.parseIndex(command, Parser.DELETE_PREFIX, tasks.getSize());
                        ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:91: error: cannot find symbol
            Task t = tasks.deleteTask(index);
            ^
  symbol:   class Task
  location: class Neo
src\main\java\neo\Neo.java:98: error: cannot find symbol
        if (command.startsWith(Parser.FIND_PREFIX)) {
                               ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:99: error: cannot find symbol
            String keyword = Parser.parseFind(command);
                             ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:100: error: cannot find symbol
            ArrayList<Task> matches = tasks.findTasks(keyword);
                      ^
  symbol:   class Task
  location: class Neo
src\main\java\neo\Neo.java:105: error: cannot find symbol
        if (command.startsWith(Parser.TODO_PREFIX)) {
                               ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:106: error: cannot find symbol
            addTask(Parser.parseTodo(command));
                    ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:110: error: cannot find symbol
        if (command.startsWith(Parser.DEADLINE_PREFIX)) {
                               ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:111: error: cannot find symbol
            addTask(Parser.parseDeadline(command));
                    ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:115: error: cannot find symbol
        if (command.startsWith(Parser.EVENT_PREFIX)) {
                               ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:116: error: cannot find symbol
            addTask(Parser.parseEvent(command));
                    ^
  symbol:   variable Parser
  location: class Neo
src\main\java\neo\Neo.java:120: error: cannot find symbol
        throw new NeoException("Unrecognized command. Try typing: todo <description>, "
                  ^
  symbol:   class NeoException
  location: class Neo
37 errors
```
