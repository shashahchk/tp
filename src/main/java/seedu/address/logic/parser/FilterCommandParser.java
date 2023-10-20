package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.Duration;
import seedu.address.model.internship.DurationWithinPredicate;
import java.util.ArrayList;
import java.util.List;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class FilterCommandParser implements InternshipParser<FilterCommand> {
    /**
     * Filters the internship list based on the argument of the filter command
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] durations = trimmedArgs.split("-");
        if (durations.length != 2 || !durations[0].matches(Duration.VALIDATION_REGEX) ||
                !durations[1].matches(Duration.VALIDATION_REGEX)) {
            throw new ParseException("Expected two duration values separated by a hyphen " +
                    "and matching the required format.");
        }

        List<Duration> durationList = new ArrayList<>();
        for (String durationStr : durations) {
            durationList.add(new Duration(durationStr));
        }

        return new FilterCommand(new DurationWithinPredicate(durationList));
    }
}
