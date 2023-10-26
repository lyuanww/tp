package seedu.cc.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.cc.commons.core.GuiSettings;
import seedu.cc.model.appointment.AppointmentEvent;
import seedu.cc.model.medicalhistory.MedicalHistoryEvent;
import seedu.cc.model.patient.Patient;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Patient> PREDICATE_SHOW_ALL_PERSONS = unused -> true;


    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs newUserPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

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
    Path getClinicBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setClinicBookFilePath(Path clinicBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setClinicBook(ReadOnlyClinicBook clinicBook);

    /** Returns the AddressBook */
    ReadOnlyClinicBook getClinicBook();

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the clinic book.
     */
    boolean hasPatient(Patient patient);

    /**
     * Deletes the given patient.
     * The patient must exist in the clinic book.
     */
    void deletePatient(Patient target);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the clinic book.
     */
    void addPatient(Patient patient);

    /**
     * Replaces the given patient {@code target} with {@code editedPatient}.
     * {@code target} must exist in the clinic book.
     * The patient identity of {@code editedPatient} must not be the same as another
     *  existing patient in the clinic book.
     */
    void setPatient(Patient target, Patient editedPatient);

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPatientList();

    ObservableList<MedicalHistoryEvent> getFilteredMedicalHistoryEventList();

    ObservableList<AppointmentEvent> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    //=========== Medical History Events =============================================================

    /**
     * Adds a medical history event to the given patient.
     * @param patient patient to add medical history event to.
     * @param medicalHistoryEvent medical history event to be added.
     */
    void addMedicalHistoryEvent(Patient patient, MedicalHistoryEvent medicalHistoryEvent);

    void listMedicalHistoryEvents(Patient patient);

    void setMedicalHistoryEvent(Patient patient, MedicalHistoryEvent medicalHistoryEventToEdit,
                                 MedicalHistoryEvent editedMedicalHistoryEvent);

    void deleteMedicalHistoryEvent(Patient patient, MedicalHistoryEvent medicalHistoryEventToDelete);

    //=========== AppointmentEvent Operations =============================================================

    void addAppointmentEventToPatient(Patient patient, AppointmentEvent appointmentEvent);

    void listAppointmentsEventForPatient(Patient patient);

    void setAppointmentEventForPatient(Patient patient, AppointmentEvent appointmentEventToEdit,
                                       AppointmentEvent editedAppointmentEvent);

    void deleteAppointmentEventForPatient(Patient patient, AppointmentEvent appointmentEventToDelete);
}
