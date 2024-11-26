package Model_classes;

public class DrugstoreOwner {
    private int ownerID;
    private String name;
    private String email;
    private String contactInfo;
    private String businessLicense;
    private String approval;
    private String password;

    // Constructor
    public DrugstoreOwner(int ownerID, String name, String email, String contactInfo, String businessLicense, String approval, String password) {
        this.ownerID = ownerID;
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
        this.businessLicense = businessLicense;
        this.approval = approval;
        this.password = password;
    }

    // Getters and Setters
    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DrugstoreOwner{" +
                "ownerID=" + ownerID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", businessLicense='" + businessLicense + '\'' +
                ", approval='" + approval + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

