package seedu.address.model.internship;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.InternshipBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternships.JANESTREET;
import static seedu.address.testutil.TypicalInternships.OPTIVER;

class InternshipTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Internship internship = new InternshipBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> internship.getRequirements().remove(0));
    }

    @Test
    public void isSameInternship() {
        // same object -> returns true
        assertTrue(JANESTREET.isSameInternship(JANESTREET));

        // null -> returns false
        assertFalse(JANESTREET.isSameInternship(null));

        // same companyName and same role, all other attributes different -> returns true
        Internship editedJaneStreet = new InternshipBuilder(JANESTREET)
                        .withApplicationStatus(VALID_APPLICATIONSTATUS_OPTIVER)
                        .withStartDate(VALID_STARTDATE_OPTIVER)
                        .withDuration(VALID_DURATION_OPTIVER).withRequirements(VALID_REQUIREMENT_OPTIVER)
                        .build();
        assertTrue(JANESTREET.isSameInternship(editedJaneStreet));

        // different companyName, all other attributes same -> returns false
        editedJaneStreet = new InternshipBuilder(JANESTREET).withCompanyName(VALID_COMPANY_NAME_OPTIVER).build();
        assertFalse(JANESTREET.isSameInternship(editedJaneStreet));

        // companyName differs in case, all other attributes same -> returns false
        Internship editedBob = new InternshipBuilder(OPTIVER).withCompanyName(VALID_COMPANY_NAME_OPTIVER.toLowerCase()).build();
        assertFalse(OPTIVER.isSameInternship(editedBob));

        // companyName has trailing spaces, all other attributes same -> returns false
        String companyNameWithTrailingSpaces = VALID_COMPANY_NAME_OPTIVER + " ";
        editedBob = new InternshipBuilder(OPTIVER).withCompanyName(companyNameWithTrailingSpaces).build();
        assertFalse(OPTIVER.isSameInternship(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Internship aliceCopy = new InternshipBuilder(JANESTREET).build();
        assertTrue(JANESTREET.equals(aliceCopy));

        // same object -> returns true
        assertTrue(JANESTREET.equals(JANESTREET));

        // null -> returns false
        assertFalse(JANESTREET.equals(null));

        // different type -> returns false
        assertFalse(JANESTREET.equals(5));

        // different internship -> returns false
        assertFalse(JANESTREET.equals(OPTIVER));

        // different companyName -> returns false
        Internship editedJaneStreet = new InternshipBuilder(JANESTREET).withCompanyName(VALID_COMPANY_NAME_OPTIVER).build();
        assertFalse(JANESTREET.equals(editedJaneStreet));

        // different role -> returns false
        editedJaneStreet = new InternshipBuilder(JANESTREET).withRole(VALID_ROLE_OPTIVER).build();
        assertFalse(JANESTREET.equals(editedJaneStreet));

        // different application status -> returns false
        editedJaneStreet = new InternshipBuilder(JANESTREET).withApplicationStatus(VALID_APPLICATIONSTATUS_OPTIVER).build();
        assertFalse(JANESTREET.equals(editedJaneStreet));

        // different startDate -> returns false
        editedJaneStreet = new InternshipBuilder(JANESTREET).withStartDate(VALID_STARTDATE_OPTIVER).build();
        assertFalse(JANESTREET.equals(editedJaneStreet));

        // different duration -> returns false
        editedJaneStreet = new InternshipBuilder(JANESTREET).withDuration(VALID_DURATION_OPTIVER).build();
        assertFalse(JANESTREET.equals(editedJaneStreet));

        // same companyName, different role -> returns false
        editedJaneStreet = new InternshipBuilder(JANESTREET).withRole(VALID_ROLE_OPTIVER).build();
        assertFalse(JANESTREET.equals(editedJaneStreet));
    }

    @Test
    public void toStringMethod() {
        String expected = Internship.class.getCanonicalName() + "{Company Name=" + JANESTREET.getCompanyName()
                + ", Role=" + JANESTREET.getRole() + ", Application Status=" + JANESTREET.getApplicationStatus()
                + ", Start Date=" + JANESTREET.getStartDate() + ", Duration=" + JANESTREET.getDuration()
                + ", Requirements=" + JANESTREET.getRequirements() + "}";
        assertEquals(expected, JANESTREET.toString());
    }
}