package seedu.address.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Internship's duration in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDuration(String)}
 */
public class Duration implements Comparable<Duration> {

    public static final String MESSAGE_CONSTRAINTS =
            "Internship durations should only contain positive integers representing months and should not be blank";
    public static final String VALIDATION_REGEX = "[1-9]\\d*";
    private final String duration;

    /**
     * Constructs a {@code Duration}.
     *
     * @param duration A valid duration in months.
     */
    public Duration(String duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        this.duration = duration;
    }

    /**
     * Verifies whether the given string constitutes a valid duration.
     *
     * @param test The given string to be tested.
     * @return A boolean representing whether the string input is valid.
     */
    public static boolean isValidDuration(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.duration;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Duration)) {
            return false;
        }

        Duration otherDuration = (Duration) other;
        return this.duration.equals(otherDuration.duration);
    }

    @Override
    public int hashCode() {
        return this.duration.hashCode();
    }

    /**
     * Returns the raw string value of the Internship's duration. This method is used exclusively by the filter command.
     * We have both a toString() method and a getDuration() method to guard against any potential changes to the
     * toString() string output that will regress the functionalities of filter.
     *
     * @return The raw string value of the Internship's duration.
     */
    public String getDuration() {
        return this.duration;
    }

    @Override
    public int compareTo(Duration other) {
        return Integer.compare(Integer.parseInt(this.duration), Integer.parseInt(other.duration));
    }
}
