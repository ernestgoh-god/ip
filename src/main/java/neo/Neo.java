package neo;

import java.util.Scanner;

/** Starts the Neo chatbot application. */
public class Neo {
    private static final String BANNER = " _   _            \n"
            + "| \\ | | ___  ___ \n"
            + "|  \\| |/ _ \\/ _ \\\n"
            + "| |\\  |  __/ (_) |\n"
            + "|_| \\_|\\___|\\___/ \n";
    private static final String SEPARATOR = "    " + "_".repeat(60);
    
    /**
     * Starts the chatbot, reads commands from standard input, and displays responses.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.print(BANNER);
        System.out.println("     Hello! I'm Neo.");
        System.out.println("     What can I do for you?");
        System.out.println(SEPARATOR);
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equals("list")) {
                System.out.println(SEPARATOR);
                System.out.println("     Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("     " + (i + 1) + "." + tasks[i]);
                }
                System.out.println(SEPARATOR);
                System.out.println();
            } else if (userInput.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(5));
                int taskIndex = taskNumber - 1;

                tasks[taskIndex].markAsDone();

                System.out.println(SEPARATOR);
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       " + tasks[taskIndex]);
                System.out.println(SEPARATOR);
                System.out.println();
            } else if (userInput.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(7));
                int taskIndex = taskNumber - 1;

                tasks[taskIndex].unmarkAsDone();

                System.out.println(SEPARATOR);
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       " + tasks[taskIndex]);
                System.out.println(SEPARATOR);
                System.out.println();
            } else {
                tasks[taskCount] = new Task(userInput);
                taskCount++;

                System.out.println(SEPARATOR);
                System.out.println("     added: " + userInput);
                System.out.println(SEPARATOR);
                System.out.println();
            }
        }

        System.out.println(SEPARATOR);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);

        scanner.close();
    }
}
