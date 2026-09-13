package neo;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import neo.exception.NeoException;
import neo.task.Deadline;
import neo.task.Event;
import neo.task.Task;
import neo.task.Todo;


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
    private static final String FILE_PATH = "./data/neo.txt";

    /**
     * Starts the chatbot, reads commands from standard input, and displays responses.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        printWelcomeMessage();
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];

        int taskCount = loadTasks(tasks);

        runChat(scanner, tasks, taskCount);

        printFarewellMessage();
        scanner.close();
    }

    /** Runs the command loop until the user requests to exit. */
    private static void runChat(Scanner scanner, Task[] tasks, int taskCount) {
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals(EXIT_COMMAND)) {
                return;
            }
            try {
                taskCount = processUserInput(userInput, tasks, taskCount);
                saveTasks(tasks, taskCount);
            } catch (NeoException e) {
                System.out.println(SEPARATOR);
                System.out.println("     Error: " + e.getMessage());
                System.out.println(SEPARATOR);
                System.out.println();
            }
        }
    }

    /** Processes one non-exit command and returns the updated task count. */
    private static int processUserInput(String userInput, Task[] tasks, int taskCount) throws NeoException {
        if (userInput.equals(LIST_COMMAND)) {
            printTaskList(tasks, taskCount);
            return taskCount;
        }

        if (userInput.startsWith(MARK_PREFIX)) {
            markTask(userInput, tasks, taskCount);
            return taskCount;
        }

        if (userInput.startsWith(UNMARK_PREFIX)) {
            unmarkTask(userInput, tasks, taskCount);
            return taskCount;
        }

        if (userInput.startsWith(TODO_PREFIX)) {
            String description = userInput.substring(TODO_PREFIX.length()).trim();
            if (description.isEmpty()) {
                throw new NeoException("The todo description is empty. Try typing: todo <description>.");
            }
            return addDetailedTask(new Todo(description), tasks, taskCount);
        }

        if (userInput.startsWith(DEADLINE_PREFIX)) {
            return addDetailedTask(createDeadline(userInput), tasks, taskCount);
        }

        if (userInput.startsWith(EVENT_PREFIX)) {
            return addDetailedTask(createEvent(userInput), tasks, taskCount);
        }

        throw new NeoException("Unrecognized command. Try typing: todo <description>, deadline <description> <date>, event <description> <date>, list, mark <index>, unmark <index>, or bye.");
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
    private static void markTask(String userInput, Task[] tasks, int taskCount) throws NeoException {
        int taskIndex = getTaskIndex(userInput, MARK_PREFIX, taskCount);
        tasks[taskIndex].markAsDone();

        System.out.println(SEPARATOR);
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("       " + tasks[taskIndex]);
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /** Marks the task identified by an unmark command as not done. */
    private static void unmarkTask(String userInput, Task[] tasks, int taskCount) throws NeoException {
        int taskIndex = getTaskIndex(userInput, UNMARK_PREFIX, taskCount);
        tasks[taskIndex].unmarkAsDone();

        System.out.println(SEPARATOR);
        System.out.println("     OK, I've marked this task as not done yet:");
        System.out.println("       " + tasks[taskIndex]);
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /** Returns the zero-based task index contained in a numbered command. */
    private static int getTaskIndex(String userInput, String commandPrefix, int taskCount) throws NeoException {
        String indexString = userInput.substring(commandPrefix.length()).trim();
        if (indexString.isEmpty()) {
            throw new NeoException("The task index is missing. Try typing: " + commandPrefix.trim() + " <index>.");
        }

        try {
            int taskIndex = Integer.parseInt(indexString) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw new NeoException("The task index is out of bounds.");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new NeoException("The task index is not a valid number.");
        }
    }

    /** Creates a deadline task from a deadline command. */
    private static Deadline createDeadline(String userInput) throws NeoException {
        String payload = userInput.substring(DEADLINE_PREFIX.length()).trim();
        if (payload.isEmpty()) {
            throw new NeoException("The deadline description is missing. Try typing: " + DEADLINE_PREFIX.trim() + " <description> /by <date>");
        }
        String[] parts = payload.split(DEADLINE_DELIMITER);
        if (parts.length < 2) {
            throw new NeoException("The deadline date is missing. Try typing: " + DEADLINE_PREFIX.trim() + " <description> /by <date>");
        }

        return new Deadline(parts[0], parts[1]);
    }

    /** Creates an event task from an event command. */
    private static Event createEvent(String userInput) throws NeoException {
        String payload = userInput.substring(EVENT_PREFIX.length()).trim();
        if (payload.isEmpty()) {
            throw new NeoException("The event description is missing. Try typing: " + EVENT_PREFIX.trim() + " <description> /from <start date> /to <end date>");
        }
        String[] fromSplit = payload.split(EVENT_FROM_DELIMITER);
        if (fromSplit.length < 2) {
            throw new NeoException("The event start date is missing. Try typing: " + EVENT_PREFIX.trim() + " <description> /from <start date> /to <end date>");
        }
        String[] toSplit = fromSplit[1].split(EVENT_TO_DELIMITER);
        if (toSplit.length < 2) {
            throw new NeoException("The event end date is missing. Try typing: " + EVENT_PREFIX.trim() + " <description> /from <start date> /to <end date>");
        }
        return new Event(fromSplit[0], toSplit[0], toSplit[1]);
    }

    /** Stores and reports a task created with a todo, deadline, or event command. */
    private static int addDetailedTask(Task task, Task[] tasks, int taskCount) throws NeoException {
        if (taskCount >= tasks.length) {
            throw new NeoException("Your task list is full! You cannot add more than " + tasks.length + " tasks.");
        }

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

    /** Prints Neo's farewell message. */
    private static void printFarewellMessage() {
        System.out.println(SEPARATOR);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    /** Saves the current task list to the hard drive. */
    private static void saveTasks(Task[] tasks, int taskCount) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);

            for (int i = 0; i < taskCount; i++) {
                writer.write(tasks[i].toSaveFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /** Loads tasks from the hard drive on startup. */
    private static int loadTasks(Task[] tasks) {
        int taskCount = 0;
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return 0; 
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                if (taskCount >= tasks.length) {
                    System.out.println("Warning: Task limit of " + tasks.length + " reached. Remaining saved tasks were ignored.");
                    break;
                }

                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    String type = line.substring(0, 1);
                    Task task = null;
                    boolean isDone = false;

                    if (type.equals("T")) {
                        String[] parts = line.split(" \\| ", 3);
                        task = new Todo(parts[2]);
                        isDone = parts[1].equals("1");
                    } else if (type.equals("D")) {
                        String[] parts = line.split(" \\| ", 4);
                        task = new Deadline(parts[2], parts[3]);
                        isDone = parts[1].equals("1");
                    } else if (type.equals("E")) {
                        String[] parts = line.split(" \\| ", 5);
                        task = new Event(parts[2], parts[3], parts[4]);
                        isDone = parts[1].equals("1");
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        tasks[taskCount] = task;
                        taskCount++;
                    }
                } catch (Exception e) {
                    System.out.println("Skipping corrupted data line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found.");
        }
        return taskCount;
    }
}
