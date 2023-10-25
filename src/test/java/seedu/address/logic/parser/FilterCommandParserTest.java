package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertInternshipParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertInternshipParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.internship.CompanyNameContainsKeywordsPredicate;
import seedu.address.model.internship.Duration;
import seedu.address.model.internship.DurationWithinRangePredicate;
import seedu.address.model.internship.RequirementContainsKeywordsPredicate;
import seedu.address.model.internship.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.StartDate;
import seedu.address.model.internship.StartDateWithinRangePredicate;

public class FilterCommandParserTest {
    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertInternshipParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_NON_EMPTY));
    }

    @Test
    public void parse_prefixWithEmptyFields_throwsParseException() {
        assertInternshipParseFailure(parser, " c/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_NON_EMPTY));
        assertInternshipParseFailure(parser, " ro/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_NON_EMPTY));
        assertInternshipParseFailure(parser, " d/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_NON_EMPTY));
        assertInternshipParseFailure(parser, " s/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_NON_EMPTY));
        assertInternshipParseFailure(parser, " re/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_NON_EMPTY));
    }

    @Test
    public void parse_twoPredicates_throwsParseException() {
        assertInternshipParseFailure(parser, " c/Google ro/swe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_ONE_PARAMETER));
    }

    @Test
    public void parse_invalidDurationArg_throwsParseException() {
        assertInternshipParseFailure(parser, " d/4to5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_DURATION_RANGE_FORMAT));
    }

    @Test
    public void parse_invalidStartDateArg_throwsParseException() {
        assertInternshipParseFailure(parser, " s/20/23/2027",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommandParser.MESSAGE_STARTDATE_RANGE_FORMAT));
    }

    @Test
    public void parse_validCompanyNameArgs_returnsFilterCommand() {
        List<String> keywords = Arrays.asList("Optiver");

        FilterCommand expectedFilterCommand =
                new FilterCommand(new CompanyNameContainsKeywordsPredicate(keywords));
        assertInternshipParseSuccess(parser, " c/Optiver", expectedFilterCommand);
    }

    @Test
    public void parse_validRoleArgs_returnsFilterCommand() {
        List<String> keywords = Arrays.asList("Developer");
        FilterCommand expectedFilterCommand = new FilterCommand(new RoleContainsKeywordsPredicate(keywords));
        assertInternshipParseSuccess(parser, " ro/Developer", expectedFilterCommand);
    }

    @Test
    public void parse_validDurationArgs_returnsFilterCommand() {
        List<Duration> durationList = Arrays.asList(new Duration("6"), new Duration("12"));

        FilterCommand expectedFilterCommand =
                new FilterCommand(new DurationWithinRangePredicate(durationList));
        assertInternshipParseSuccess(parser, " d/6-12", expectedFilterCommand);
    }

    @Test
    public void parse_validStartDateArgs_returnsFilterCommand() {
        List<StartDate> startDateList = Arrays.asList(new StartDate("20/03/2023"), new StartDate("20/06/2023"));

        FilterCommand expectedFilterCommand =
                new FilterCommand(new StartDateWithinRangePredicate(startDateList));
        assertInternshipParseSuccess(parser, " s/20/03/2023-20/06/2023", expectedFilterCommand);
    }

    @Test
    public void parse_validRequirementsArgs_returnsFilterCommand() {
        List<String> keywords = Arrays.asList("C++");

        FilterCommand expectedFilterCommand =
                new FilterCommand(new RequirementContainsKeywordsPredicate(keywords));
        assertInternshipParseSuccess(parser, " re/C++", expectedFilterCommand);

        List<String> multipleKeywords = Arrays.asList(new String[]{"Java", "Python"});
        FilterCommand expectedFilterCommandMultipleKeywords =
                new FilterCommand(new RequirementContainsKeywordsPredicate(multipleKeywords));
        assertInternshipParseSuccess(parser, " re/Java re/Python",
                expectedFilterCommandMultipleKeywords);
    }
}
