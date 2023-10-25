package seedu.address.model.internship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.InternshipBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CompanyNameContainsKeywordsPredicate firstPredicate =
                new CompanyNameContainsKeywordsPredicate(firstPredicateKeywordList);
        CompanyNameContainsKeywordsPredicate secondPredicate =
                new CompanyNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompanyNameContainsKeywordsPredicate firstPredicateCopy = new CompanyNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different internship -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        CompanyNameContainsKeywordsPredicate predicate = new CompanyNameContainsKeywordsPredicate(
                Collections.singletonList("Jane"));
        assertTrue(predicate.test(new InternshipBuilder().withCompanyName("Jane Street").build()));

        // Multiple keywords
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("Jane", "Street"));
        assertTrue(predicate.test(new InternshipBuilder().withCompanyName("Jane Street").build()));

        // Only one matching keyword
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("Bob", "Jane"));
        assertTrue(predicate.test(new InternshipBuilder().withCompanyName("Jane Street").build()));

        // Mixed-case keywords
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("jAne", "strEet"));
        assertTrue(predicate.test(new InternshipBuilder().withCompanyName("Jane Street").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CompanyNameContainsKeywordsPredicate predicate = new CompanyNameContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new InternshipBuilder().withCompanyName("Jane Street").build()));

        // Non-matching keyword
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new InternshipBuilder().withCompanyName("Jane Street").build()));

        // Keywords match role, duration and start date, but does not match name
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("Software", "Engineer", "3", "20/01/2023"));
        assertFalse(predicate.test(new InternshipBuilder().withCompanyName("Jane Street")
                .withRole("Software Engineer").withDuration("3").withStartDate("20/01/2023").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        CompanyNameContainsKeywordsPredicate predicate = new CompanyNameContainsKeywordsPredicate(keywords);

        String expected = CompanyNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
