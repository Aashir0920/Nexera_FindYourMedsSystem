package Model_classes;

public class Feedback {
    private int feedbackID;
    private String text;
    private String date;
    private int pharmacyID;
    private int uid;

    // Constructor
    public Feedback(int feedbackID, String text, String date, int pharmacyID, int uid) {
        this.feedbackID = feedbackID;
        this.text = text;
        this.date = date;
        this.pharmacyID = pharmacyID;
        this.uid = uid;
    }

    // Getters and Setters
    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackID=" + feedbackID +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", pharmacyID=" + pharmacyID +
                ", uid=" + uid +
                '}';
    }
}

