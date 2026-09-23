package neo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import neo.exception.NeoException;
import neo.task.Deadline;
import neo.task.Event;
import neo.task.Task;
import neo.task.Todo;

/**
 * Handles the loading and saving of task data to a persistent text file.
 * Creates the necessary directories and files if they do not exist, and parses
 * the stored text back into recognizable Task objects.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The relative or absolute file path where task data is saved.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the tasks from the storage file into an ArrayList.
     * Reads the file line by line, identifies the task type (Todo, Deadline, Event),
     * and reconstructs the objects. Corrupted lines are skipped automatically.
     *
     * @return An {@code ArrayList<Task>} containing the tasks parsed from the file.
     * @throws NeoException If the data file does not exist at the specified path.
     */
    public ArrayList<Task> load() throws NeoException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new NeoException("Data file not found.");
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
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
                        loadedTasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping corrupted data line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found.");
        }
        return loadedTasks;
    }

    /**
     * Saves the provided list of tasks to the storage file.
     * Overwrites the existing file content with the string representations of the
     * tasks. Automatically creates the parent directories if they are missing.
     *
     * @param tasks The {@code ArrayList<Task>} containing the current tasks to save.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toSaveFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}