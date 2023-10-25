package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.InternshipModel;
import seedu.address.model.internship.Internship;

/**
 * Filters for relevant internships by category.
 */
public class FilterCommand extends InternshipCommand {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = "filter: Filters the internship list. "
            + "Parameters: PREFIX/KEYWORDS or PREFIX/X-Y for duration and start date\n"
            + "Example: filter ro/developer\n"
            + "Example: filter d/2-3\n"
            + "To reset filters: filter default";

    private final Predicate<Internship> predicate;

    public FilterCommand(Predicate<Internship> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(InternshipModel model) {
        requireNonNull(model);
        model.updateFilteredInternshipList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_INTERNSHIPS_LISTED_OVERVIEW, model.getFilteredInternshipList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
