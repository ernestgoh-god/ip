import java.util.Scanner;

/**
 * Starts the Neo chatbot application.
 */
public class Neo {
    public static void main(String[] args) {
        String banner = " _   _            \n"
                + "| \\ | | ___  ___ \n"
                + "|  \\| |/ _ \\/ _ \\\n"
                + "| |\\  |  __/ (_) |\n"
                + "|_| \\_|\\___|\\___/ \n";
        String separator = "    " + "_".repeat(60);

        System.out.println(separator);
        System.out.print(banner);
        System.out.println("     Hello! I'm Neo.");
        System.out.println("     What can I do for you?");
        System.out.println(separator);
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
        int taskCount = 0;

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equals("list")) {
                System.out.println(separator);
                System.out.println("     Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    String status = isDone[i] ? "X" : " ";
                    System.out.println("     " + (i + 1) + ".[" + status + "] " + tasks[i]);
                }
                System.out.println(separator);
                System.out.println();
            } else if (userInput.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(5));
                int taskIndex = taskNumber - 1;

                isDone[taskIndex] = true;

                System.out.println(separator);
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       [X] " + tasks[taskIndex]);
                System.out.println(separator);
                System.out.println();
            } else if (userInput.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(7));
                int taskIndex = taskNumber - 1;

                isDone[taskIndex] = false;

                System.out.println(separator);
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       [ ] " + tasks[taskIndex]);
                System.out.println(separator);
                System.out.println();
            } else {
                tasks[taskCount] = userInput;
                isDone[taskCount] = false;
                taskCount++;

                System.out.println(separator);
                System.out.println("     added: " + userInput);
                System.out.println(separator);
                System.out.println();
            }
        }

        System.out.println(separator);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(separator);

        scanner.close();
    }
}
