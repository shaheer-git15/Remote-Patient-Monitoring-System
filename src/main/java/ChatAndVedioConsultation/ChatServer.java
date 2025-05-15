package ChatAndVedioConsultation;

import DatabaseConnector.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChatServer {

    // Save a message to the chat_logs table
    public static void logMessage(int senderID, int receiverID, String message) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO chat_logs (sender_id, receiver_id, message) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderID);
            ps.setInt(2, receiverID);
            ps.setString(3, message);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to log chat message: " + e.getMessage());
        }
    }

    // View complete chat history between two users
    public static void viewChat(int userA, int userB) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT sender_id, message, timestamp FROM chat_logs " +
                    "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) " +
                    "ORDER BY timestamp ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userA);
            ps.setInt(2, userB);
            ps.setInt(3, userB);
            ps.setInt(4, userA);
            ResultSet rs = ps.executeQuery();

            System.out.println("--- Chat between " + userA + " and " + userB + " ---");
            boolean empty = true;

            while (rs.next()) {
                empty = false;
                int sender = rs.getInt("sender_id");
                String msg = rs.getString("message");
                String time = rs.getString("timestamp");
                System.out.println("[" + time + "] From " + sender + ": " + msg);
            }

            if (empty) {
                System.out.println("No chat history between these users.");
            }

        } catch (Exception e) {
            System.out.println("Failed to fetch chat history: " + e.getMessage());
        }
    }
}