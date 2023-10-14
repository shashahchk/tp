package seedu.address.model.internship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

class DurationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Duration(null));
    }

    @Test
    public void constructor_invalidDuration_throwsIllegalArgumentException() {
        String invalidDuration = "";
        assertThrows(IllegalArgumentException.class, () -> new Duration(invalidDuration));
    }

    @Test
    public void isValidDuration() {
        // null duration
        assertThrows(NullPointerException.class, () -> Duration.isValidDuration(null));

        // invalid durations
        assertFalse(Duration.isValidDuration("")); // empty string
        assertFalse(Duration.isValidDuration(" ")); // spaces only
        assertFalse(Duration.isValidDuration("a")); // non number
        assertFalse(Duration.isValidDuration("2.2")); // non-integer number
        assertFalse(Duration.isValidDuration("-2")); // non-positive number

        // valid durations
        assertTrue(Duration.isValidDuration("1"));
        assertTrue(Duration.isValidDuration("2"));
        assertTrue(Duration.isValidDuration("3"));
        assertTrue(Duration.isValidDuration("4"));
        assertTrue(Duration.isValidDuration("5"));
        assertTrue(Duration.isValidDuration("6"));
        assertTrue(Duration.isValidDuration("7"));
        assertTrue(Duration.isValidDuration("8"));
        assertTrue(Duration.isValidDuration("9"));
    }

    @Test
    public void equals() {
        Duration duration = new Duration("2");

        // same values -> returns true
        assertTrue(duration.equals(new Duration("2")));

        // same object -> returns true
        assertTrue(duration.equals(duration));

        // null -> returns false
        assertFalse(duration.equals(null));

        // different types -> returns false
        assertFalse(duration.equals(5.0f));

        // different values -> returns false
        assertFalse(duration.equals(new Duration("5")));
    }
}