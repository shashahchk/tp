package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

public class ApplicationStatusContainsKeywordsPredicate implements Predicate<Internship> {
    private final List<String> keywords;

    public ApplicationStatusContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Internship internship) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(internship
                        .getApplicationStatus().applicationStatus, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApplicationStatusContainsKeywordsPredicate)) {
            return false;
        }

        ApplicationStatusContainsKeywordsPredicate otherApplicationStatusContainsKeywordsPredicate = (ApplicationStatusContainsKeywordsPredicate) other;
        return keywords.equals(otherApplicationStatusContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

}
