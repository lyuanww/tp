package seedu.cc.model.medicalhistory;

import java.time.LocalDateTime;

/**
 * Represents a patient's medical history event.
 */
public class MedicalHistoryEvent {

    private final MedicalCondition medicalCondition;
    private final Treatment treatment;
    private final LocalDateTime date;

    /**
     * Creates a MedicalHistoryEvent with the specified medical condition, treatment and date.
     * @param medicalCondition
     * @param treatment
     * @param date
     */
    public MedicalHistoryEvent(MedicalCondition medicalCondition, Treatment treatment, LocalDateTime date) {
        this.medicalCondition = medicalCondition;
        this.treatment = treatment;
        this.date = date;
    }

    public MedicalCondition getMedicalCondition() {
        return medicalCondition;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "MedicalHistoryEvent{"
                + "medicalCondition='" + medicalCondition + '\''
                + ", treatment='" + treatment + '\''
                + ", date=" + date
                + '}';
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedicalHistoryEvent)) {
            return false;
        }

        MedicalHistoryEvent otherMedicalHistoryEvent = (MedicalHistoryEvent) other;
        return medicalCondition.equals(otherMedicalHistoryEvent.medicalCondition)
                && treatment.equals(otherMedicalHistoryEvent.treatment)
                && date.equals(otherMedicalHistoryEvent.date);
    }
}

