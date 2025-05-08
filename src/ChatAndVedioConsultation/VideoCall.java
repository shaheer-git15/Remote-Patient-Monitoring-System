package ChatAndVedioConsultation;

import UserManagement.Doctor;
import UserManagement.Patient;

import java.util.Scanner;

public class VideoCall {
    static Scanner sc = new Scanner(System.in);

    public static void startCall(int userID) {
        boolean isDoctor = Doctor.doctors.containsKey(userID);

        if (isDoctor) {
            System.out.print("Enter Patient ID to start video call: ");
            int pid = sc.nextInt();
            if (!Patient.patients.containsKey(pid)) {
                System.out.println("Patient not found.");
                return;
            }
            simulateCall(userID, pid);
        } else {
            System.out.print("Enter Doctor ID to start video call: ");
            int did = sc.nextInt();
            if (!Doctor.doctors.containsKey(did)) {
                System.out.println("Doctor not found.");
                return;
            }
            simulateCall(userID, did);
        }
    }

    private static void simulateCall(int callerID, int receiverID) {
        System.out.println("Connecting video call between " + callerID + " and " + receiverID + "...");
        System.out.println("[Simulation] Google Meet or Zoom link launched.");
    }
}
