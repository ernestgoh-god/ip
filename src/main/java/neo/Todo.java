package neo;

/** Represents a todo task without a specific date or time attached. */
public class Todo extends Task {
    
    /**
     * Creates an incomplete todo task with the specified description.
     *
     * @param description Text that describes the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}