package neo;

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

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}