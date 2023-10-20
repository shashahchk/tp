package seedu.address.model.internship;

import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

public class StartDateWithinRangePredicate implements Predicate<Internship> {
    private final List<StartDate> startDateRange;
    public StartDateWithinRangePredicate(List<StartDate> startDateRange) {
        this.startDateRange = startDateRange;
    }

    @Override
    public boolean test(Internship internship) {
        StartDate internshipStartDate = internship.getStartDate();
        StartDate startDateLower = startDateRange.get(0);
        StartDate startDateUpper = startDateRange.get(1);

        return internshipStartDate.compareTo(startDateLower) >= 0
                && internshipStartDate.compareTo(startDateUpper) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StartDateWithinRangePredicate)) {
            return false;
        }

        StartDateWithinRangePredicate otherStartDateWithinRangePredicate = (StartDateWithinRangePredicate) other;
        return startDateRange.equals(otherStartDateWithinRangePredicate.startDateRange);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("startDateRange", startDateRange).toString();
    }
}
