package networkbook.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static networkbook.commons.util.CollectionUtil.requireAllNonNull;

import networkbook.commons.core.index.Index;
import networkbook.logic.commands.exceptions.CommandException;
import networkbook.model.person.Course;
import networkbook.model.person.Email;
import networkbook.model.person.Graduation;
import networkbook.model.person.Link;
import networkbook.model.person.Name;
import networkbook.model.person.Person;
import networkbook.model.person.Phone;
import networkbook.model.person.Priority;
import networkbook.model.person.Specialisation;
import networkbook.model.tag.Tag;
import networkbook.model.util.UniqueList;

public class EditPersonDescriptor {
    private static String MESSAGE_INVALID_PHONE_INDEX = "The phone index provided is invalid.";
    private static String MESSAGE_INVALID_EMAIL_INDEX = "The email index provided is invalid.";
    private static String MESSAGE_INVALID_LINK_INDEX = "The link index provided is invalid.";
    private static String MESSAGE_INVALID_COURSE_INDEX = "The course index provided is invalid.";
    private static String MESSAGE_INVALID_SPECIALISATION_INDEX = "The specialisation index provided is invalid.";
    private static String MESSAGE_INVALID_TAG_INDEX = "The tag index provided is invalid.";
    private Name name;
    private UniqueList<Phone> phones;
    private UniqueList<Email> emails;
    private UniqueList<Link> links;
    private Graduation graduation;
    private UniqueList<Course> courses;
    private UniqueList<Specialisation> specialisations;
    private UniqueList<Tag> tags;
    private Priority priority;

    public EditPersonDescriptor(Person person) {
        this.name = person.getName();
        this.phones = person.getPhones();
        this.emails = person.getEmails();
        this.links = person.getLinks();
        this.graduation = person.getGraduation().orElse(null);
        this.courses = person.getCourses();
        this.specialisations = person.getSpecialisations();
        this.tags = person.getTags();
        this.priority = person.getPriority().orElse(null);
    }

    public void setName(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public void setPhone(Index index, Phone phone) throws CommandException {
        requireAllNonNull(index, phone);
        if (index.getZeroBased() >= this.phones.size()) {
            throw new CommandException(MESSAGE_INVALID_PHONE_INDEX);
        }
        this.phones.setItem(index.getZeroBased(), phone);
    }

    public void setEmail(Index index, Email email) throws CommandException {
        requireAllNonNull(index, email);
        if (index.getZeroBased() >= this.emails.size()) {
            throw new CommandException(MESSAGE_INVALID_EMAIL_INDEX);
        }
        this.emails.setItem(index.getZeroBased(), email);
    }

    public void setLink(Index index, Link link) throws CommandException {
        requireAllNonNull(index, link);
        if (index.getZeroBased() >= this.links.size()) {
            throw new CommandException(MESSAGE_INVALID_LINK_INDEX);
        }
        this.links.setItem(index.getZeroBased(), link);
    }

    public void setGraduation(Graduation graduation) {
        requireNonNull(graduation);
        this.graduation = graduation;
    }

    public void setCourse(Index index, Course course) throws CommandException {
        requireAllNonNull(index, course);
        if (index.getZeroBased() >= this.courses.size()) {
            throw new CommandException(MESSAGE_INVALID_COURSE_INDEX);
        }
        this.courses.setItem(index.getZeroBased(), course);
    }

    public void setSpecialisation(Index index, Specialisation specialisation) throws CommandException {
        requireAllNonNull(index, specialisation);
        if (index.getZeroBased() >= this.specialisations.size()) {
            throw new CommandException(MESSAGE_INVALID_SPECIALISATION_INDEX);
        }
        this.specialisations.setItem(index.getZeroBased(), specialisation);
    }

    public void setTag(Index index, Tag tag) throws CommandException {
        requireAllNonNull(index, tag);
        if (index.getZeroBased() >= this.tags.size()) {
            throw new CommandException(MESSAGE_INVALID_TAG_INDEX);
        }
        this.tags.setItem(index.getZeroBased(), tag);
    }

    public void setPriority(Priority priority) {
        requireNonNull(priority);
        this.priority = priority;
    }

    public Person toPerson() {
        return new Person(name, phones, emails, links, graduation, courses, specialisations, tags, priority);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof EditPersonDescriptor)) {
            return false;
        }

        EditPersonDescriptor otherDescriptor = (EditPersonDescriptor) object;
        return this.name.equals(otherDescriptor.name)
                && this.phones.equals(otherDescriptor.phones)
                && this.emails.equals(otherDescriptor.emails)
                && this.links.equals(otherDescriptor.links)
                && this.graduation.equals(otherDescriptor.graduation)
                && this.courses.equals(otherDescriptor.courses)
                && this.specialisations.equals(otherDescriptor.specialisations)
                && this.tags.equals(otherDescriptor.tags)
                && this.priority.equals(otherDescriptor.priority);
    }
}
