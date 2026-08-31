package neo;

import java.util.Scanner;

/** Starts the Neo chatbot application. */
public class Neo {
    private static final String BANNER = " _   _\n"
            + "| \\ | | ___  ___\n"
            + "|  \\| |/ _ \\/ _ \\\n"
            + "| |\\  |  __/ (_) |\n"
            + "|_| \\_|\\___|\\___/\n";
    private static final String SEPARATOR = "    " + "_".repeat(60);
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_PREFIX = "mark ";
    private static final String UNMARK_PREFIX = "unmark ";
    private static final String TODO_PREFIX = "todo ";
    private static final String DEADLINE_PREFIX = "deadline ";
    private static final String EVENT_PREFIX = "event ";
    private static final String DEADLINE_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    /**
     * Starts the chatbot, reads commands from standard input, and displays responses.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];

        runChat(scanner, tasks);
        printFarewellMessage();
        scanner.close();
    }

    /** Runs the command loop until the user requests to exit. */
    private static void runChat(Scanner scanner, Task[] tasks) {
        int taskCount = 0;

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals(EXIT_COMMAND)) {
                return;
            }

            taskCount = processUserInput(userInput, tasks, taskCount);
        }
    }

    /** Processes one non-exit command and returns the updated task count. */
    private static int processUserInput(String userInput, Task[] tasks, int taskCount) {
        if (userInput.equals(LIST_COMMAND)) {
            printTaskList(tasks, taskCount);
            return taskCount;
        }

        if (userInput.startsWith(MARK_PREFIX)) {
            markTask(userInput, tasks);
            return taskCount;
        }

        if (userInput.startsWith(UNMARK_PREFIX)) {
            unmarkTask(userInput, tasks);
            return taskCount;
        }

        if (userInput.startsWith(TODO_PREFIX)) {
            return addDetailedTask(new Todo(userInput.substring(TODO_PREFIX.length())), tasks, taskCount);
        }

        if (userInput.startsWith(DEADLINE_PREFIX)) {
            return addDetailedTask(createDeadline(userInput), tasks, taskCount);
        }

        if (userInput.startsWith(EVENT_PREFIX)) {
            return addDetailedTask(createEvent(userInput), tasks, taskCount);
        }

        return addPlainTask(userInput, tasks, taskCount);
    }

    /** Prints Neo's initial greeting. */
    private static void printWelcomeMessage() {
        System.out.println(SEPARATOR);
        System.out.print(BANNER);
        System.out.println("     Hello! I'm Neo.");
        System.out.println("     What can I do for you?");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /** Prints every task currently stored in the task list. */
    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println(SEPARATOR);
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("     " + (i + 1) + "." + tasks[i]);
        }
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /** Marks the task identified by a mark command as done. */
    private static void markTask(String userInput, Task[] tasks) {
        int taskIndex = getTaskIndex(userInput, MARK_PREFIX);
        tasks[taskIndex].markAsDone();

        System.out.println(SEPARATOR);
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("       " + tasks[taskIndex]);
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /** Marks the task identified by an unmark command as not done. */
    private static void unmarkTask(String userInput, Task[] tasks) {
        int taskIndex = getTaskIndex(userInput, UNMARK_PREFIX);
        tasks[taskIndex].unmarkAsDone();

        System.out.println(SEPARATOR);
        System.out.println("     OK, I've marked this task as not done yet:");
        System.out.println("       " + tasks[taskIndex]);
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /** Returns the zero-based task index contained in a numbered command. */
    private static int getTaskIndex(String userInput, String commandPrefix) {
        return Integer.parseInt(userInput.substring(commandPrefix.length())) - 1;
    }

    /** Creates a deadline task from a deadline command. */
    private static Deadline createDeadline(String userInput) {
        String payload = userInput.substring(DEADLINE_PREFIX.length());
        String[] parts = payload.split(DEADLINE_DELIMITER);
        return new Deadline(parts[0], parts[1]);
    }

    /** Creates an event task from an event command. */
    private static Event createEvent(String userInput) {
        String payload = userInput.substring(EVENT_PREFIX.length());
        String[] fromSplit = payload.split(EVENT_FROM_DELIMITER);
        String[] toSplit = fromSplit[1].split(EVENT_TO_DELIMITER);
        return new Event(fromSplit[0], toSplit[0], toSplit[1]);
    }

    /** Stores and reports a task created with a todo, deadline, or event command. */
    private static int addDetailedTask(Task task, Task[] tasks, int taskCount) {
        tasks[taskCount] = task;
        int newTaskCount = taskCount + 1;

        System.out.println(SEPARATOR);
        System.out.println("     Got it. I've added this task:");
        System.out.println("     " + task);
        System.out.println("     Now you have " + newTaskCount + " tasks in the list.");
        System.out.println(SEPARATOR);
        System.out.println();
        return newTaskCount;
    }

    /** Stores and reports a task created without a task-type command. */
    private static int addPlainTask(String description, Task[] tasks, int taskCount) {
        tasks[taskCount] = new Task(description);

        System.out.println(SEPARATOR);
        System.out.println("     added: " + description);
        System.out.println(SEPARATOR);
        System.out.println();
        return taskCount + 1;
    }

    /** Prints Neo's farewell message. */
    private static void printFarewellMessage() {
        System.out.println(SEPARATOR);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }
}
