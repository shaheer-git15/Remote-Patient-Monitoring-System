package UserManagement;

import java.util.HashSet;
import java.util.Scanner;

public class User {
    static Scanner sc = new Scanner(System.in);
    private static HashSet<Integer> userIDs = new HashSet<>();

    private int userID;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String email;
    private String password;

    public User(int userID, String name, int age, String gender, String address, String email, String password) {
        setUserID(userID);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    private void setUserID(int userID) {
        if (userIDs.contains(userID))
            throw new IllegalArgumentException("User ID '" + userID + "' already exists.");
        else {
            this.userID = userID;
            userIDs.add(userID);
        }
    }

    // Getters
    public int getUserID() { return userID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public static void login(int userID, String password, String role) {
        User user = null;
        switch (role.toLowerCase()) {
            case "doctor" -> user = Doctor.getDoctor(userID);
            case "patient" -> user = Patient.getPatient(userID);
            case "administrator" -> user = Administrator.getAdministrator(userID);
            default -> {
                System.out.println("Invalid role.");
                return;
            }
        }

        if (user == null) {
            System.out.println("User ID not found.");
        } else if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
        } else {
            switch (role.toLowerCase()) {
                case "doctor" -> ((Doctor) user).loginDoctor();
                case "patient" -> ((Patient) user).loginPatient();
                case "administrator" -> ((Administrator) user).loginAdministrator();
            }
        }
    }
}
