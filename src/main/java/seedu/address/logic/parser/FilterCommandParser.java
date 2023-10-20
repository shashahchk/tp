package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.*;
import seedu.address.model.InternshipModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUIREMENT;

public class FilterCommandParser implements InternshipParser<FilterCommand> {
    /**
     * Filters the internship list based on the argument of the filter command
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_ROLE, PREFIX_APPLICATION_STATUS,
                        PREFIX_START_DATE, PREFIX_DURATION, PREFIX_REQUIREMENT);
        if (args.trim().equalsIgnoreCase("default")) {
            return new FilterCommand(InternshipModel.PREDICATE_SHOW_ALL_INTERNSHIPS);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.isOnlyOnePrefixPresent()) {
            throw new ParseException("Filter command should accept only one parameter at a time.");
        }

        Predicate<Internship> predicate;

        if (argMultimap.getValue(PREFIX_COMPANY_NAME).isPresent()) {
            predicate = parseCompanyName(argMultimap);
        } else if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            predicate = parseRole(argMultimap);
        } else if (argMultimap.getValue(PREFIX_APPLICATION_STATUS).isPresent()) {
            predicate = parseApplicationStatus(argMultimap);
        } else if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            predicate = parseStartDateRange(argMultimap);
        } else if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            predicate = parseDuration(argMultimap);
        } else if (argMultimap.getAllValues(PREFIX_REQUIREMENT).size() > 0) {
            predicate = parseRequirement(argMultimap);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(predicate);
    }

    private Predicate<Internship> parseCompanyName(ArgumentMultimap argMultimap) {
        String companyName = argMultimap.getValue(PREFIX_COMPANY_NAME).get();
        return new NameContainsKeywordsPredicate(Arrays.asList(companyName));
    }

    private Predicate<Internship> parseRole(ArgumentMultimap argMultimap) {
        String role = argMultimap.getValue(PREFIX_ROLE).get();
        return new RoleContainsKeywordsPredicate(Arrays.asList(role));
    }

    private Predicate<Internship> parseApplicationStatus(ArgumentMultimap argMultimap) {
        String applicationStatus = argMultimap.getValue(PREFIX_APPLICATION_STATUS).get();
        return new ApplicationStatusContainsKeywordsPredicate(Arrays.asList(applicationStatus));
    }

    private Predicate<Internship> parseStartDateRange(ArgumentMultimap argMultimap) throws ParseException {
        String[] dateRange = argMultimap.getValue(PREFIX_START_DATE).get().split("-");
        StartDate startDateLower = ParserUtil.parseStartDate(dateRange[0].trim());
        StartDate startDateUpper = ParserUtil.parseStartDate(dateRange[1].trim());
        return new StartDateWithinRangePredicate(Arrays.asList(new StartDate[] {startDateLower, startDateUpper}));
    }

    private Predicate<Internship> parseDuration(ArgumentMultimap argMultimap) {
        String durations = argMultimap.getValue(PREFIX_DURATION).get();
        String[] durationArr = durations.split("-");
        List<Duration> durationList = new ArrayList<>();
        for (String durationStr : durationArr) {
            durationList.add(new Duration(durationStr));
        }
        return new DurationWithinRangePredicate(durationList);
    }

    private Predicate<Internship> parseRequirement(ArgumentMultimap argMultimap) {
        List<String> requirements = argMultimap.getAllValues(PREFIX_REQUIREMENT);
        return new RequirementContainsKeywordsPredicate(requirements);
    }
}
