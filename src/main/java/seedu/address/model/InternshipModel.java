package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.internship.Internship;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface InternshipModel {

    // DELETE AFTER FULL TRANSITION
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Internship> PREDICATE_SHOW_ALL_INTERNSHIPS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyInternshipUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyInternshipUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getInternshipBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setInternshipBookFilePath(Path internshipBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setInternshipBook(ReadOnlyInternshipBook internshipBook);

    /** Returns the AddressBook */
    ReadOnlyInternshipBook getInternshipBook();

    // DELETE THIS WHEN hasInternship is fully functional
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasInternship(Internship internship);

    /**
     * Deletes the given internship.
     * The internship must exist in the address book.
     */
    void deleteInternship(Internship target);


    void createInternship(Internship internship);

    /**
     * Replaces the given internship {@code target} with {@code editedInternship}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedInternship} must not be the same as another existing internship in the
     * address book.
     */
    void setInternship(Internship target, Internship editedInternship);

    /** Returns an unmodifiable view of the filtered internship list */
    ObservableList<Internship> getFilteredInternshipList();


    /**
     * Updates the filter of the filtered internship list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    default void updateFilteredInternshipList(Predicate<Internship> predicate) {

    }
}
