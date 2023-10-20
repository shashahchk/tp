package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.InternshipBook;
import seedu.address.model.internship.Internship;

/**
 * A utility class containing a list of {@code Internship} objects to be used in tests.
 */
public class TypicalInternships {

    public static final Internship JANESTREET = new InternshipBuilder().withCompanyName("Jane Street")
            .withRole("Software Engineer")
            .withDuration("3")
            .withRequirements(new String[]{"2 years of experience"})
            .withStartDate("07/05/2023").build();

    public static final Internship OPTIVER = new InternshipBuilder().withCompanyName("Optiver")
            .withRole("Software Engineer")
            .withDuration("3")
            .withRequirements(new String[]{"3 years of experience"})
            .withStartDate("07/05/2023").build();

    public static final Internship CITADEL = new InternshipBuilder().withCompanyName("Citadel")
            .withRole("Software Engineer")
            .withDuration("3")
            .withRequirements(new String[]{"4 years of experience"})
            .withStartDate("07/05/2023").build();

    public static final Internship GOVTECH = new InternshipBuilder().withCompanyName("Govtech")
            .withRole("UI Designer")
            .withDuration("1")
            .withRequirements(new String[]{"40 years of experience"})
            .withStartDate("01/05/2021").build();

    private TypicalInternships() {} // prevents instantiation

    /**
     * Returns an {@code InternshipBook} with all the typical internships.
     */
    public static InternshipBook getTypicalInternshipBook() {
        InternshipBook ib = new InternshipBook();
        for (Internship internship : getTypicalInternships()) {
            ib.createInternship(internship);
        }
        return ib;
    }

    public static List<Internship> getTypicalInternships() {
        return new ArrayList<>(Arrays.asList(JANESTREET, OPTIVER, CITADEL, GOVTECH));
    }
}
