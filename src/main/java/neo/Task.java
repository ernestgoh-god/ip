package neo;

/** Represents a task in Neo's task list. */
public class Task {
    /** The text that describes this task. */
    private String description;

    /** Whether this task has been completed. */
    private boolean isDone;

    /**
     * Creates an incomplete task with the specified description.
     *
     * @param description Text that describes the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns the display marker for this task's completion state.
     *
     * @return `X` when the task is done, otherwise a blank space.
     */
    public String getStatus() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        String statusIcon = isDone ? "[X]" : "[ ]";
        return statusIcon + " " + description;
    }
}
