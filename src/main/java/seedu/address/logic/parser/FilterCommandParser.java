package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUIREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.ApplicationStatusContainsKeywordsPredicate;
import seedu.address.model.internship.CompanyNameContainsKeywordsPredicate;
import seedu.address.model.internship.Duration;
import seedu.address.model.internship.DurationWithinRangePredicate;
import seedu.address.model.internship.Internship;
import seedu.address.model.InternshipModel;
import seedu.address.model.internship.RequirementContainsKeywordsPredicate;
import seedu.address.model.internship.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.StartDate;
import seedu.address.model.internship.StartDateWithinRangePredicate;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.List;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements InternshipParser<FilterCommand> {
    /**
     * Filters the internship list based on the argument of the filter command
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public static final String MESSAGE_ONE_PARAMETER = "filter command should accept only one parameter at a time.";
    public static final String MESSAGE_STARTDATE_RANGE_FORMAT = "date range should be in the format DD/MM/YYYY-DD/MM/YYYY";
    public static final String MESSAGE_DURATION_RANGE_FORMAT = "duration range should be in the format X-Y";
    public static final String MESSAGE_NON_EMPTY = "filter command's parameter cannot be empty.";

    public FilterCommand parse(String args) throws ParseException {
        if (args.trim().length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NON_EMPTY));
        }

        if (args.equalsIgnoreCase(" default")) {
            return new FilterCommand(InternshipModel.PREDICATE_SHOW_ALL_INTERNSHIPS);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_ROLE, PREFIX_APPLICATION_STATUS,
                        PREFIX_DURATION, PREFIX_START_DATE, PREFIX_REQUIREMENT);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.isOnlyOnePrefixPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_ONE_PARAMETER));
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterCommandParser.MESSAGE_NON_EMPTY));
        }
        return new FilterCommand(predicate);
    }

    private Predicate<Internship> parseCompanyName(ArgumentMultimap argMultimap) throws ParseException {
        String companyName = argMultimap.getValue(PREFIX_COMPANY_NAME).get();
        if (companyName.trim().length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NON_EMPTY));
        }
        return new CompanyNameContainsKeywordsPredicate(Arrays.asList(companyName));
    }

    private Predicate<Internship> parseRole(ArgumentMultimap argMultimap) throws ParseException {
        String role = argMultimap.getValue(PREFIX_ROLE).get();
        if (role.trim().length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NON_EMPTY));
        }
        return new RoleContainsKeywordsPredicate(Arrays.asList(role));
    }

    private Predicate<Internship> parseApplicationStatus(ArgumentMultimap argMultimap) throws ParseException {
        String applicationStatus = argMultimap.getValue(PREFIX_APPLICATION_STATUS).get();
        if (applicationStatus.trim().length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NON_EMPTY));
        }
        return new ApplicationStatusContainsKeywordsPredicate(Arrays.asList(applicationStatus));
    }

    private Predicate<Internship> parseStartDateRange(ArgumentMultimap argMultimap) throws ParseException {
        String dates = argMultimap.getValue(PREFIX_START_DATE).get();
        if (dates.trim().length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NON_EMPTY));
        }

        String[] dateRange = dates.split("-");
        if (dateRange.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_STARTDATE_RANGE_FORMAT));
        }

        StartDate startDateLower;
        StartDate startDateUpper;

        try {
            startDateLower = ParserUtil.parseStartDate(dateRange[0].trim());
            startDateUpper = ParserUtil.parseStartDate(dateRange[1].trim());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_STARTDATE_RANGE_FORMAT));
        }

        return new StartDateWithinRangePredicate(Arrays.asList(new StartDate[] {startDateLower, startDateUpper}));
    }

    private Predicate<Internship> parseDuration(ArgumentMultimap argMultimap) throws ParseException {
        String durations = argMultimap.getValue(PREFIX_DURATION).get();
        if (durations.trim().length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NON_EMPTY));
        }

        String[] durationArr = durations.split("-");
        if (durationArr.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_DURATION_RANGE_FORMAT));
        }

        List<Duration> durationList = new ArrayList<>();
        try {
            for (String durationStr : durationArr) {
                durationList.add(new Duration(durationStr));
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_DURATION_RANGE_FORMAT));
        }

        return new DurationWithinRangePredicate(durationList);
    }

    private Predicate<Internship> parseRequirement(ArgumentMultimap argMultimap) throws ParseException {
        List<String> requirements = argMultimap.getAllValues(PREFIX_REQUIREMENT);
        if (requirements.stream().allMatch(String::isEmpty) || requirements.stream().allMatch(
                str -> str.trim().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NON_EMPTY));
        }
        return new RequirementContainsKeywordsPredicate(requirements);
    }
}
