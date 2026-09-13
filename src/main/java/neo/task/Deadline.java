package neo.task;

/** Represents a task that needs to be done before a specific date/time. */
public class Deadline extends Task {
    /** The deadline date/time for the task. */
    private String by;

    /**
     * Creates an incomplete deadline task with a description and a deadline time.
     *
     * @param description Text that describes the task.
     * @param by The date/time the task needs to be completed by.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /** Returns a string representation of this deadline task for display. */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /** Returns a string representation of this deadline task for saving to a file. */
    @Override
    public String toSaveFormat() {
        return "D | " + super.toSaveFormat() + " | " + by;
    }
}