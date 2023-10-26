package seedu.cc.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.cc.model.appointment.AppointmentEvent;
import seedu.cc.model.person.Address;
import seedu.cc.model.person.Email;
import seedu.cc.model.person.Name;
import seedu.cc.model.person.Phone;
import seedu.cc.model.tag.Tag;


public class PatientTest {

    private Patient patient;
    private Patient patientNullAppointment;

    @BeforeEach
    public void setUp() {
        // Sample data for testing
        Name name = new Name("John Doe");
        Nric nric = new Nric("M1234567A");
        Phone phone = new Phone("98765432");
        Email email = new Email("johndoe@example.com");
        Address address = new Address("123, Jurong West Ave 6, #08-111");
        AppointmentEvent appointmentEvent = new AppointmentEvent("2023-12-10", "10:00");
        Set<Tag> tags = new HashSet<>();

        patient = new Patient(name, nric, phone, email, address, appointmentEvent, tags);
        patientNullAppointment = new Patient(name, nric, phone, email, address, tags);
    }

    @Test
    public void getName() {
        assertEquals(new Name("John Doe"), patient.getName());
    }

    @Test
    public void getNric() {
        assertEquals(new Nric("M1234567A"), patient.getNric());
    }

    @Test
    public void getPhone() {
        assertEquals(new Phone("98765432"), patient.getPhone());
    }

    @Test
    public void getEmail() {
        assertEquals(new Email("johndoe@example.com"), patient.getEmail());
    }

    @Test
    public void getAddress() {
        assertEquals(new Address("123, Jurong West Ave 6, #08-111"), patient.getAddress());
    }

    @Test
    public void getAppointment() {
        assertEquals(new AppointmentEvent("2023-12-10", "10:00"), patient.getAppointment());
        assertNull(patientNullAppointment.getAppointment());
    }

    @Test
    public void getTags() {
        assertTrue(patient.getTags().isEmpty());
    }

    @Test
    public void isSamePerson_samePatient_returnsTrue() {
        assertTrue(patient.isSamePerson(patient));
    }
}
