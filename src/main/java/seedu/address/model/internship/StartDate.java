package seedu.address.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



/**
 * Represents an Internship's start date in Flagship.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Start Date should only contain numbers and slashes. It must follow the form DD/MM/YYYY";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final LocalDate startDate;

    /**
     * Constructs a {@code StartDate}.
     *
     * @param startDate A valid start date.
     */
    public StartDate(String startDate) {
        requireNonNull(startDate);
        checkArgument(isValidStartDate(startDate), MESSAGE_CONSTRAINTS);
        this.startDate = LocalDate.parse(startDate, DATE_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid StartDate.
     */
    public static boolean isValidStartDate(String test) {
        try {
            LocalDate.parse(test, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.startDate.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        StartDate otherStartDate = (StartDate) other;
        return this.startDate.equals(otherStartDate.startDate);
    }

    @Override
    public int hashCode() {
        return this.startDate.hashCode();
    }

    public int compareTo(StartDate other) {
        return this.startDate.compareTo(other.startDate);
    }
}
