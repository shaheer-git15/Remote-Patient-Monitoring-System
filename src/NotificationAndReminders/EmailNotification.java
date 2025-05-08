package NotificationAndReminders;

public class EmailNotification implements Notifiable{
    public void send(String recipient, String content) {
        System.out.println("[EMAIL SENT] To: " + recipient);
        System.out.println("Message: " + content);
    }
}