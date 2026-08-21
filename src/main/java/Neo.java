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
        String separator = "_".repeat(60);

        System.out.println(separator);
        System.out.print(banner);
        System.out.println("Hello! I'm Neo.");
        System.out.println("What can I do for you?");
        System.out.println(separator);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(separator);
    }
}
