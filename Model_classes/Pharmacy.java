package Model_classes;

public class Pharmacy {
    private int pharmacyID;
    private String name;
    private String location;
    private String contactInfo;
    private int ownerID;

    // Constructor
    public Pharmacy(int pharmacyID, String name, String location, String contactInfo, int ownerID) {
        this.pharmacyID = pharmacyID;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.ownerID = ownerID;
    }

    // Getters and Setters
    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "pharmacyID=" + pharmacyID +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", ownerID=" + ownerID +
                '}';
    }
}
