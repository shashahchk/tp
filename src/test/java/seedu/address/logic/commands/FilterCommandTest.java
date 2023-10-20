package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INTERNSHIPS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertInternshipCommandSuccess;
import static seedu.address.testutil.TypicalInternships.CITADEL;
import static seedu.address.testutil.TypicalInternships.GOVTECH;
import static seedu.address.testutil.TypicalInternships.JANESTREET;
import static seedu.address.testutil.TypicalInternships.OPTIVER;
import static seedu.address.testutil.TypicalInternships.getTypicalInternshipBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.InternshipModel;
import seedu.address.model.InternshipModelManager;
import seedu.address.model.InternshipUserPrefs;
import seedu.address.model.internship.Duration;
import seedu.address.model.internship.DurationWithinRangePredicate;

/**
 * Contains integration tests (interaction with the InternshipModel) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private InternshipModel internshipModel = new InternshipModelManager(getTypicalInternshipBook(),
            new InternshipUserPrefs());
    private InternshipModel expectedInternshipModel = new InternshipModelManager(getTypicalInternshipBook(),
            new InternshipUserPrefs());

    @Test
    public void equals() {
        List<Duration> firstRange = Arrays.asList(new Duration("1"), new Duration("5"));
        List<Duration> secondRange = Arrays.asList(new Duration("2"), new Duration("6"));

        DurationWithinRangePredicate firstPredicate = new DurationWithinRangePredicate(firstRange);
        DurationWithinRangePredicate secondPredicate = new DurationWithinRangePredicate(secondRange);

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_oneRange_noInternshipFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIPS_LISTED_OVERVIEW, 0);
        DurationWithinRangePredicate predicate = preparePredicate("10-12");
        FilterCommand command = new FilterCommand(predicate);
        expectedInternshipModel.updateFilteredInternshipList(predicate);
        assertInternshipCommandSuccess(command, internshipModel, expectedMessage, expectedInternshipModel);
        assertEquals(Collections.emptyList(), internshipModel.getFilteredInternshipList());
    }

    @Test
    public void execute_oneRange_oneInternshipFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIPS_LISTED_OVERVIEW, 1);
        DurationWithinRangePredicate predicate = preparePredicate("1-2");
        FilterCommand command = new FilterCommand(predicate);
        expectedInternshipModel.updateFilteredInternshipList(predicate);
        assertInternshipCommandSuccess(command, internshipModel, expectedMessage, expectedInternshipModel);
        assertEquals(Arrays.asList(GOVTECH), internshipModel.getFilteredInternshipList());
    }
    @Test
    public void execute_oneRange_multipleInternshipsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIPS_LISTED_OVERVIEW, 3);
        DurationWithinRangePredicate predicate = preparePredicate("3-6");
        FilterCommand command = new FilterCommand(predicate);
        expectedInternshipModel.updateFilteredInternshipList(predicate);
        assertInternshipCommandSuccess(command, internshipModel, expectedMessage, expectedInternshipModel);
        assertEquals(Arrays.asList(JANESTREET, OPTIVER, CITADEL), internshipModel.getFilteredInternshipList());
    }

    @Test
    public void toStringMethod() {
        List<Duration> range = Arrays.asList(new Duration("6"), new Duration("12"));
        DurationWithinRangePredicate predicate = new DurationWithinRangePredicate(range);
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code DurationWithinPredicate}.
     */
    private DurationWithinRangePredicate preparePredicate(String userInput) {
        String[] durations = userInput.split("-");

        List<Duration> durationList = new ArrayList<>();
        for (String durationStr : durations) {
            durationList.add(new Duration(durationStr));
        }

        return new DurationWithinRangePredicate(durationList);
    }
}
