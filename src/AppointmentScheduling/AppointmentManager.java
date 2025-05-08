package AppointmentScheduling;

import UserManagement.Doctor;
import UserManagement.Patient;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class AppointmentManager {

    public static HashMap<Integer, Appointment> appointments = new HashMap<>();

    public static void bookAppointment(int patientID, int doctorID) {
        Doctor doctor = Doctor.getDoctor(doctorID);
        Patient patient = Patient.getPatient(patientID);

        if (doctor == null) {
            System.out.println("Doctor with ID " + doctorID + " does not exist.");
            return;
        }
        if (patient == null) {
            System.out.println("Patient with ID " + patientID + " does not exist.");
            return;
        }

        Appointment appointment = new Appointment(patientID, doctorID);
        appointments.put(appointment.getAppointmentID(), appointment);
        doctor.appointments.put(appointment.getAppointmentID(), appointment);
        patient.appointments.put(appointment.getAppointmentID(), appointment);

        System.out.println("Appointment booked successfully. Appointment ID: " + appointment.getAppointmentID());
    }

    public static void cancelAppointment(int appointmentID) {
        if (!appointments.containsKey(appointmentID)) {
            System.out.println("Appointment ID " + appointmentID + " does not exist.");
            return;
        }

        Appointment appt = appointments.get(appointmentID);
        Doctor doctor = Doctor.getDoctor(appt.getDoctorID());
        Patient patient = Patient.getPatient(appt.getPatientID());

        if (doctor != null) doctor.appointments.remove(appointmentID);
        if (patient != null) patient.appointments.remove(appointmentID);

        appointments.remove(appointmentID);
        System.out.println("Appointment ID " + appointmentID + " canceled successfully.");
    }

    public static void approveAppointment(int appointmentID, int doctorID) {
        if (!appointments.containsKey(appointmentID)) {
            System.out.println("Invalid appointment ID.");
            return;
        }

        Doctor doctor = Doctor.getDoctor(doctorID);
        Appointment appt = appointments.get(appointmentID);

        if (doctor == null || !doctor.appointments.containsKey(appointmentID)) {
            System.out.println("Doctor or appointment mismatch.");
            return;
        }

        appt.setStatus("Approved");
        doctor.approveAppointments.add(appointmentID);
        System.out.println("Appointment approved.");
    }

    public static void rejectAppointment(int appointmentID, int doctorID) {
        if (!appointments.containsKey(appointmentID)) {
            System.out.println("Invalid appointment ID.");
            return;
        }

        Doctor doctor = Doctor.getDoctor(doctorID);

        if (doctor != null) {
            doctor.appointments.remove(appointmentID);
            doctor.approveAppointments.remove(appointmentID);
        }

        appointments.remove(appointmentID);
        System.out.println("Appointment rejected and removed.");
    }

    public static void viewApprovedAppointments(int doctorID) {
        Doctor doctor = Doctor.getDoctor(doctorID);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        System.out.println("Approved Appointments for Doctor ID " + doctorID + ": " + doctor.approveAppointments);
    }
}
