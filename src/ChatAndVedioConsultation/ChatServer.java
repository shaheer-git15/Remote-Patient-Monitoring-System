package ChatAndVedioConsultation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    public static Map<String, ArrayList<String>> chatHistory = new HashMap<>();

    public static void logMessage(int senderID, int receiverID, String message) {
        String key = senderID + "-" + receiverID;
        chatHistory.putIfAbsent(key, new ArrayList<>());
        chatHistory.get(key).add("From " + senderID + ": " + message);
    }

    public static void viewChat(int userA, int userB) {
        String key1 = userA + "-" + userB;
        String key2 = userB + "-" + userA;

        ArrayList<String> messages = new ArrayList<>();
        if (chatHistory.containsKey(key1)) messages.addAll(chatHistory.get(key1));
        if (chatHistory.containsKey(key2)) messages.addAll(chatHistory.get(key2));

        if (messages.isEmpty()) {
            System.out.println("No chat history between these users.");
        } else {
            System.out.println("--- Chat between " + userA + " and " + userB + " ---");
            for (String msg : messages) {
                System.out.println(msg);
            }
        }
    }
}
