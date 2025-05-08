package AppointmentScheduling;

import UserManagement.Doctor;

import java.time.LocalDate;

public class Appointment {
    public static int appointmentCounter = 1;

    private int appointmentID;
    private Doctor doctor;
    private int patientID;
    private int doctorID;
    private String status;
    private String date;

    public Appointment(int patientID, int doctorID) {
        this.appointmentID = appointmentCounter++;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.status = "Pending";
        this.date = LocalDate.now().toString();

        this.doctor = Doctor.getDoctor(doctorID);
        if (doctor != null) {
            doctor.appointments.put(appointmentID, this);
        }
    }

    public int getDoctorID() { return doctorID; }
    public int getAppointmentID() { return appointmentID; }
    public int getPatientID() { return patientID; }
    public String getStatus() { return status; }
    public String getDate() { return date; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", doctorID=" + doctorID +
                ", patientID=" + patientID +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}' + "\n";
    }
}
