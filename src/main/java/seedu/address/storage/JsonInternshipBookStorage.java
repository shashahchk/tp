package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyInternshipBook;

/**
 * A class to access InternshipBook data stored as a json file on the hard disk.
 */
public class JsonInternshipBookStorage implements InternshipBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInternshipBookStorage.class);

    private Path filePath;

    public JsonInternshipBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInternshipBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInternshipBook> readInternshipBook() throws DataLoadingException {
        return readInternshipBook(filePath);
    }

    /**
     * Similar to {@link #readInternshipBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyInternshipBook> readInternshipBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableInternshipBook> jsonInternshipBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableInternshipBook.class);
        if (!jsonInternshipBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInternshipBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveInternshipBook(ReadOnlyInternshipBook internshipBook) throws IOException {
        saveInternshipBook(internshipBook, filePath);
    }

    /**
     * Similar to {@link #saveInternshipBook(ReadOnlyInternshipBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInternshipBook(ReadOnlyInternshipBook internshipBook, Path filePath) throws IOException {
        requireNonNull(internshipBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInternshipBook(internshipBook), filePath);
    }

}
