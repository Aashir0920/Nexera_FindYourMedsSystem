package Model_classes;

public class Wishlist {
    private int reqID;
    private int userID;
    private int medID;
    private int pharmacyID;

    // Constructor
    public Wishlist(int reqID, int userID, int medID, int pharmacyID) {
        this.reqID = reqID;
        this.userID = userID;
        this.medID = medID;
        this.pharmacyID = pharmacyID;
    }

    // Getters and Setters
    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMedID() {
        return medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "reqID=" + reqID +
                ", userID=" + userID +
                ", medID=" + medID +
                ", pharmacyID=" + pharmacyID +
                '}';
    }
}
