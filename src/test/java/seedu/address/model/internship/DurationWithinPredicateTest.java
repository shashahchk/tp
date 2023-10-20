package seedu.address.model.internship;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

public class DurationWithinPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        seedu.address.model.person.NameContainsKeywordsPredicate firstPredicate =
                new seedu.address.model.person.NameContainsKeywordsPredicate(firstPredicateKeywordList);
        seedu.address.model.person.NameContainsKeywordsPredicate secondPredicate =
                new seedu.address.model.person.NameContainsKeywordsPredicate(secondPredicateKeywordList);
        Assertions.assertTrue(firstPredicate.equals(firstPredicate));
        seedu.address.model.person.NameContainsKeywordsPredicate firstPredicateCopy =
                new seedu.address.model.person.NameContainsKeywordsPredicate(firstPredicateKeywordList);
        Assertions.assertTrue(firstPredicate.equals(firstPredicateCopy));
        Assertions.assertFalse(firstPredicate.equals(1));
        Assertions.assertFalse(firstPredicate.equals((Object) null));
        Assertions.assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        seedu.address.model.person.NameContainsKeywordsPredicate predicate =
                new seedu.address.model.person.NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        Assertions.assertTrue(predicate.test((new PersonBuilder()).withName("Alice Bob").build()));
        predicate = new seedu.address.model.person.NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        Assertions.assertTrue(predicate.test((new PersonBuilder()).withName("Alice Bob").build()));
        predicate = new seedu.address.model.person.NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        Assertions.assertTrue(predicate.test((new PersonBuilder()).withName("Alice Carol").build()));
        predicate = new seedu.address.model.person.NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        Assertions.assertTrue(predicate.test((new PersonBuilder()).withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        seedu.address.model.person.NameContainsKeywordsPredicate predicate =
                new seedu.address.model.person.NameContainsKeywordsPredicate(Collections.emptyList());
        Assertions.assertFalse(predicate.test((new PersonBuilder()).withName("Alice").build()));
        predicate = new seedu.address.model.person.NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        Assertions.assertFalse(predicate.test((new PersonBuilder()).withName("Alice Bob").build()));
        predicate = new seedu.address.model.person.NameContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        Assertions.assertFalse(predicate.test((new PersonBuilder()).withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        seedu.address.model.person.NameContainsKeywordsPredicate predicate =
                new seedu.address.model.person.NameContainsKeywordsPredicate(keywords);
        String var10000 = NameContainsKeywordsPredicate.class.getCanonicalName();
        String expected = var10000 + "{keywords=" + String.valueOf(keywords) + "}";
        Assertions.assertEquals(expected, predicate.toString());
    }
}
