package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

public class DurationWithinRangePredicate implements Predicate<Internship> {
    private final List<Duration> durationRange;
    public DurationWithinRangePredicate(List<Duration> durationRange) {
        this.durationRange = durationRange;
    }

    @Override
    public boolean test(Internship internship) {
        Duration internshipDuration = internship.getDuration();
        Duration startDuration = durationRange.get(0);
        Duration endDuration = durationRange.get(1);

        return internshipDuration.compareTo(startDuration) >= 0
                && internshipDuration.compareTo(endDuration) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DurationWithinRangePredicate)) {
            return false;
        }

        DurationWithinRangePredicate otherDurationWithinPredicate = (DurationWithinRangePredicate) other;
        return durationRange.equals(otherDurationWithinPredicate.durationRange);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("durationRange", durationRange).toString();
    }
}
