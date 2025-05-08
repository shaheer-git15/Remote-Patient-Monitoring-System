package Doctor_PatientInteraction;

import java.util.ArrayList;
import java.util.Scanner;

public class Feedback {
    private String feedback = "Nil";
    private int patientId;
    private int doctorId;

    static Scanner sc = new Scanner(System.in);
    ArrayList<String> medicines = new ArrayList<>();
    ArrayList<String> schedules = new ArrayList<>();

    public Feedback(int patientId, int doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public int getPatientid() {
        return patientId;
    }

    public void prescribeMedicines() {
        System.out.print("How many medicines to prescribe? ");
        int count = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.print("Enter medicine " + (i + 1) + ": ");
            String med = sc.nextLine();
            medicines.add(med);
        }
    }

    public void prescribeSchedule() {
        for (String med : medicines) {
            System.out.print("Enter schedule for " + med + ": ");
            String sched = sc.nextLine();
            schedules.add(sched);
        }

        collectFeedback();
    }

    private void collectFeedback() {
        System.out.print("Leave feedback for this patient? (Y/N): ");
        char ch = sc.next().charAt(0);
        sc.nextLine();

        if (ch == 'Y' || ch == 'y') {
            System.out.print("Enter feedback: ");
            feedback = sc.nextLine();
        } else if (ch == 'N' || ch == 'n') {
            feedback = "";
        } else {
            System.out.println("Invalid input. Try again.");
            collectFeedback();
        }
    }

    @Override
    public String toString() {
        return "\n--- Prescription ---" +
                "\nDoctor ID   : " + doctorId +
                "\nPatient ID  : " + patientId +
                "\nMedicines   : " + medicines +
                "\nSchedules   : " + schedules +
                "\nFeedback    : " + (feedback.isEmpty() ? "No feedback" : feedback);
    }
}
