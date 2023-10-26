package seedu.cc.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.cc.model.appointment.AppointmentEvent;
import seedu.cc.model.medicalhistory.MedicalHistoryEvent;

/**
 * An UI component that displays information of a {@code Appointment Event}.
 */
public class AppointmentsEventCard extends UiPart<Region> {
    private static final String FXML = "MedicalHistoryEventCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final AppointmentEvent appointmentEvent;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label time;

    /**
     * Creates a {@code PatientCode} with the given {@code Patient} and index to display.
     */
    public AppointmentsEventCard(AppointmentEvent appointmentEvent, int displayedIndex) {
        super(FXML);
        this.appointmentEvent = appointmentEvent;
        date.setText(appointmentEvent.getLocalDate().toString());
        time.setText(appointmentEvent.getLocalTime().toString());
    }
}
