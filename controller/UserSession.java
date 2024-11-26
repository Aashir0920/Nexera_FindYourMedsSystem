package controller;

public class UserSession {

    private static UserSession instance; // Singleton instance
    private int userID;                  // Logged-in user ID
    private String username;             // Optional: Add other details if needed

    // Private constructor to prevent external instantiation
    private UserSession() {}

    // Get the single instance of UserSession
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Setter for userID
    public void setUserID(int userID) {
        this.userID = userID;
    }

    // Getter for userID
    public int getUserID() {
        return this.userID;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for username
    public String getUsername() {
        return this.username;
    }

    // Clear the session when the user logs out
    public void clearSession() {
        userID = 0;
        username = null;
    }
}
