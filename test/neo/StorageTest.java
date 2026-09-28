package neo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import neo.task.Deadline;
import neo.task.Event;
import neo.task.Task;
import neo.task.Todo;

/** Checks storage round trips and migration using an isolated temporary file. */
public class StorageTest {
    /**
     * Runs the storage checks and removes their temporary data afterward.
     *
     * @param args Unused command-line arguments.
     * @throws Exception If a storage operation or regression check fails.
     */
    public static void main(String[] args) throws Exception {
        Path directory = Files.createTempDirectory(Path.of("out"), "neo-storage-");
        Path file = directory.resolve("neo.txt");
        try {
            checkEncodedRoundTrip(file);
            checkLegacyMigration(file);
            checkCorruptedRecords(file);
            System.out.println("PASS: 3 storage regression checks");
        } finally {
            Files.deleteIfExists(file);
            Files.delete(directory);
        }
    }

    /** Checks all text fields and completion states after loading through a new Storage instance. */
    private static void checkEncodedRoundTrip(Path file) throws Exception {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read A | B \\ notes\nnext line: café 雪"));
        tasks.add(new Deadline("compare A | B", "Friday | Saturday"));
        tasks.add(new Event("meet A | B", "9am | 10am", "11am | noon"));
        tasks.add(new Todo(""));
        tasks.add(new Deadline("empty deadline", ""));
        tasks.add(new Event("empty times", "", ""));
        tasks.get(0).markAsDone();
        tasks.get(1).markAsDone();
        tasks.get(2).markAsDone();

        new Storage(file.toString()).save(tasks);
        checkVersionedRecords(file, tasks.size());
        checkTasks(tasks, new Storage(file.toString()).load());
    }

    /** Checks that plain text stays literal and old and new records can coexist during migration. */
    private static void checkLegacyMigration(Path file) throws Exception {
        String legacyData = "T | 1 | TWFu | literal text\n"
                + "D | 0 | submit report | Friday | evening\n"
                + "E | 1 | team sync | 10am | 11am\n"
                + "V2 | T | 0 | bmV3\n";
        Files.writeString(file, legacyData, StandardCharsets.UTF_8);
        ArrayList<Task> expected = new ArrayList<>();
        expected.add(new Todo("TWFu | literal text"));
        expected.add(new Deadline("submit report", "Friday | evening"));
        expected.add(new Event("team sync", "10am", "11am"));
        expected.add(new Todo("new"));
        expected.get(0).markAsDone();
        expected.get(2).markAsDone();

        ArrayList<Task> loadedTasks = new Storage(file.toString()).load();
        checkTasks(expected, loadedTasks);
        new Storage(file.toString()).save(loadedTasks);
        checkVersionedRecords(file, expected.size());
        checkTasks(expected, new Storage(file.toString()).load());
    }

    /** Checks that malformed V2 records are skipped without discarding valid following tasks. */
    private static void checkCorruptedRecords(Path file) throws Exception {
        String corruptedData = "V2 | D | 0 | ZGVzYw==\n"
                + "V2 | T | 0 | %%%\n"
                + "V2 | T | 2 | bmV3\n"
                + "V2 | T | 0 | bmV3 | extra\n"
                + "V2 | T | 1 | c2FmZQ==\n";
        Files.writeString(file, corruptedData, StandardCharsets.UTF_8);
        ArrayList<Task> expected = new ArrayList<>();
        expected.add(new Todo("safe"));
        expected.get(0).markAsDone();
        checkTasks(expected, new Storage(file.toString()).load());
    }

    /** Checks task types, descriptions, completion states, and displayed date/time values. */
    private static void checkTasks(ArrayList<Task> expected, ArrayList<Task> actual) {
        if (expected.size() != actual.size()) {
            throw new AssertionError("Task count changed after loading.");
        }
        for (int i = 0; i < expected.size(); i++) {
            Task expectedTask = expected.get(i);
            Task actualTask = actual.get(i);
            if (!expectedTask.getClass().equals(actualTask.getClass())
                    || !expectedTask.getDescription().equals(actualTask.getDescription())
                    || !expectedTask.getStatus().equals(actualTask.getStatus())
                    || !expectedTask.toString().equals(actualTask.toString())) {
                throw new AssertionError("Task " + (i + 1) + " changed after loading: " + actualTask);
            }
        }
    }

    /** Checks that every task occupies exactly one versioned line, even if its text contains newlines. */
    private static void checkVersionedRecords(Path file, int taskCount) throws Exception {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        if (lines.size() != taskCount) {
            throw new AssertionError("Expected one storage line per task.");
        }
        for (String line : lines) {
            if (!line.startsWith("V2 | ")) {
                throw new AssertionError("Saved task is missing its format version.");
            }
        }
    }
}
