package neo;

/** Represents a task that starts at a specific time and ends at a specific time. */
public class Event extends Task {
    /** The start date/time of the event. */
    private String from;
    
    /** The end date/time of the event. */
    private String to;

    /**
     * Creates an incomplete event task with a description, start time, and end time.
     *
     * @param description Text that describes the event.
     * @param from The starting date/time of the event.
     * @param to The ending date/time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}