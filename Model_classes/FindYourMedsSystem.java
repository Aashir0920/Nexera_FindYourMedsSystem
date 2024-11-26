package Model_classes;

public class FindYourMedsSystem {
    private int systemID;
    private String adminName;
    private String adminContact;
    private String adminEmail;
    private String password;

    // Constructor
    public FindYourMedsSystem(int systemID, String adminName, String adminContact, String adminEmail, String password) {
        this.systemID = systemID;
        this.adminName = adminName;
        this.adminContact = adminContact;
        this.adminEmail = adminEmail;
        this.password = password;
    }

    // Getters and Setters
    public int getSystemID() {
        return systemID;
    }

    public void setSystemID(int systemID) {
        this.systemID = systemID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminContact() {
        return adminContact;
    }

    public void setAdminContact(String adminContact) {
        this.adminContact = adminContact;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "FindYourMedsSystem{" +
                "systemID=" + systemID +
                ", adminName='" + adminName + '\'' +
                ", adminContact='" + adminContact + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
