package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertInternshipParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertInternshipParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.internship.Duration;
import seedu.address.model.internship.DurationWithinRangePredicate;

public class FilterCommandParserTest {
    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertInternshipParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertInternshipParseFailure(parser, "4 to 5",
                "Expected two duration values separated by a hyphen "
                        + "and matching the required format.");
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        List<Duration> range = Arrays.asList(new Duration("6"), new Duration("12"));

        FilterCommand expectedFilterCommand =
                new FilterCommand(new DurationWithinRangePredicate(range));
        assertInternshipParseSuccess(parser, "6-12", expectedFilterCommand);
    }
}
