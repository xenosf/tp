package networkbook.logic.commands.edit;

import static networkbook.testutil.Assert.assertThrows;
import static networkbook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static networkbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static networkbook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import networkbook.logic.Messages;
import networkbook.model.Model;
import networkbook.model.ModelManager;
import networkbook.model.NetworkBook;
import networkbook.model.UserPrefs;
import networkbook.model.person.Course;
import networkbook.model.person.Email;
import networkbook.model.person.Link;
import networkbook.model.person.Person;
import networkbook.model.person.Phone;
import networkbook.model.person.Specialisation;
import networkbook.model.tag.Tag;
import networkbook.model.util.UniqueList;
import networkbook.testutil.TypicalIndexes;
import networkbook.testutil.TypicalPersons;


/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {
    private final Model model = new ModelManager(TypicalPersons.getTypicalNetworkBook(), new UserPrefs());
    private static final EditAction VALID_EDIT_ACTION = editPersonDescriptor -> {};

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditCommand(null, VALID_EDIT_ACTION));
        assertThrows(NullPointerException.class, () -> new EditCommand(EditCommandUtil.VALID_INDEX, null));
        assertThrows(NullPointerException.class, () -> new EditCommand(null, null));
    }

    @Test
    public void execute_validName_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new Person(
                EditCommandUtil.VALID_NAME,
                originalPerson.getPhones(),
                originalPerson.getEmails(),
                originalPerson.getLinks(),
                originalPerson.getGraduation().orElse(null),
                originalPerson.getCourses(),
                originalPerson.getSpecialisations(),
                originalPerson.getTags(),
                originalPerson.getPriority().orElse(null)
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setName(EditCommandUtil.VALID_NAME));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPhoneAndIndex_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        UniqueList<Phone> newPhoneList = originalPerson.getPhones();
        newPhoneList.setItem(EditCommandUtil.VALID_INDEX.getZeroBased(), EditCommandUtil.VALID_PHONE);
        Person editedPerson = new Person(
                originalPerson.getName(),
                newPhoneList,
                originalPerson.getEmails(),
                originalPerson.getLinks(),
                originalPerson.getGraduation().orElse(null),
                originalPerson.getCourses(),
                originalPerson.getSpecialisations(),
                originalPerson.getTags(),
                originalPerson.getPriority().orElse(null)
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setPhone(
                        EditCommandUtil.VALID_INDEX, EditCommandUtil.VALID_PHONE));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPhoneAndInvalidIndex_throwsCommandException() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setPhone(
                        EditCommandUtil.INVALID_INDEX, EditCommandUtil.VALID_PHONE));
        String expectedMessage = EditPersonDescriptor.MESSAGE_INVALID_PHONE_INDEX;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_validEmailAndIndex_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        UniqueList<Email> newEmailList = originalPerson.getEmails();
        newEmailList.setItem(EditCommandUtil.VALID_INDEX.getZeroBased(), EditCommandUtil.VALID_EMAIL);
        Person editedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhones(),
                newEmailList,
                originalPerson.getLinks(),
                originalPerson.getGraduation().orElse(null),
                originalPerson.getCourses(),
                originalPerson.getSpecialisations(),
                originalPerson.getTags(),
                originalPerson.getPriority().orElse(null)
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setEmail(
                        EditCommandUtil.VALID_INDEX, EditCommandUtil.VALID_EMAIL));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEmailAndInvalidIndex_throwsCommandException() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setEmail(
                        EditCommandUtil.INVALID_INDEX, EditCommandUtil.VALID_EMAIL));
        String expectedMessage = EditPersonDescriptor.MESSAGE_INVALID_EMAIL_INDEX;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_validLinkAndIndex_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        UniqueList<Link> newLinkList = originalPerson.getLinks();
        newLinkList.setItem(EditCommandUtil.VALID_INDEX.getZeroBased(), EditCommandUtil.VALID_LINK);
        Person editedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhones(),
                originalPerson.getEmails(),
                newLinkList,
                originalPerson.getGraduation().orElse(null),
                originalPerson.getCourses(),
                originalPerson.getSpecialisations(),
                originalPerson.getTags(),
                originalPerson.getPriority().orElse(null)
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setLink(
                        EditCommandUtil.VALID_INDEX, EditCommandUtil.VALID_LINK));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validLinkAndInvalidIndex_throwsCommandException() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setLink(
                        EditCommandUtil.INVALID_INDEX, EditCommandUtil.VALID_LINK));
        String expectedMessage = EditPersonDescriptor.MESSAGE_INVALID_LINK_INDEX;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_validGraduation_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhones(),
                originalPerson.getEmails(),
                originalPerson.getLinks(),
                EditCommandUtil.VALID_GRADUATION,
                originalPerson.getCourses(),
                originalPerson.getSpecialisations(),
                originalPerson.getTags(),
                originalPerson.getPriority().orElse(null)
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setGraduation(EditCommandUtil.VALID_GRADUATION));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCourseAndIndex_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        UniqueList<Course> newCourseList = originalPerson.getCourses();
        newCourseList.setItem(EditCommandUtil.VALID_INDEX.getZeroBased(), EditCommandUtil.VALID_COURSE);
        Person editedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhones(),
                originalPerson.getEmails(),
                originalPerson.getLinks(),
                originalPerson.getGraduation().orElse(null),
                newCourseList,
                originalPerson.getSpecialisations(),
                originalPerson.getTags(),
                originalPerson.getPriority().orElse(null)
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setCourse(
                        EditCommandUtil.VALID_INDEX, EditCommandUtil.VALID_COURSE));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCourseAndInvalidIndex_throwsCommandException() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setCourse(
                        EditCommandUtil.INVALID_INDEX, EditCommandUtil.VALID_COURSE));
        String expectedMessage = EditPersonDescriptor.MESSAGE_INVALID_COURSE_INDEX;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_validSpecialisationAndIndex_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        UniqueList<Specialisation> newSpecialisationList = originalPerson.getSpecialisations();
        newSpecialisationList.setItem(EditCommandUtil.VALID_INDEX.getZeroBased(), EditCommandUtil.VALID_SPECIALISATION);
        Person editedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhones(),
                originalPerson.getEmails(),
                originalPerson.getLinks(),
                originalPerson.getGraduation().orElse(null),
                originalPerson.getCourses(),
                newSpecialisationList,
                originalPerson.getTags(),
                originalPerson.getPriority().orElse(null)
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setSpecialisation(
                        EditCommandUtil.VALID_INDEX, EditCommandUtil.VALID_SPECIALISATION));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validSpecialisationAndInvalidIndex_throwsCommandException() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setSpecialisation(
                        EditCommandUtil.INVALID_INDEX, EditCommandUtil.VALID_SPECIALISATION));
        String expectedMessage = EditPersonDescriptor.MESSAGE_INVALID_SPECIALISATION_INDEX;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_validTagAndIndex_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        UniqueList<Tag> newTagList = originalPerson.getTags();
        newTagList.setItem(EditCommandUtil.VALID_INDEX.getZeroBased(), EditCommandUtil.VALID_TAG);
        Person editedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhones(),
                originalPerson.getEmails(),
                originalPerson.getLinks(),
                originalPerson.getGraduation().orElse(null),
                originalPerson.getCourses(),
                originalPerson.getSpecialisations(),
                newTagList,
                originalPerson.getPriority().orElse(null)
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setTag(
                        EditCommandUtil.VALID_INDEX, EditCommandUtil.VALID_TAG));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagAndInvalidIndex_throwsCommandException() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setTag(
                        EditCommandUtil.INVALID_INDEX, EditCommandUtil.VALID_TAG));
        String expectedMessage = EditPersonDescriptor.MESSAGE_INVALID_TAG_INDEX;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_validPriority_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhones(),
                originalPerson.getEmails(),
                originalPerson.getLinks(),
                originalPerson.getGraduation().orElse(null),
                originalPerson.getCourses(),
                originalPerson.getSpecialisations(),
                originalPerson.getTags(),
                EditCommandUtil.VALID_PRIORITY
        );
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editPersonDescriptor -> editPersonDescriptor.setPriority(EditCommandUtil.VALID_PRIORITY));

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetworkBook(model.getNetworkBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
}
