package Model_classes;

public class Orders {
    private int orderID;
    private String orderDate;
    private int quantity;
    private double total;
    private String paymentStatus;
    private String approvalStatus;
    private int userID;
    private int pharmacyID;
    private int medicineID;
    private String orderType;

    // Constructor
    public Orders(int orderID, String orderDate, int quantity, double total, String paymentStatus, String approvalStatus, int userID, int pharmacyID, int medicineID, String orderType) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.total = total;
        this.paymentStatus = paymentStatus;
        this.approvalStatus = approvalStatus;
        this.userID = userID;
        this.pharmacyID = pharmacyID;
        this.medicineID = medicineID;
        this.orderType = orderType;
    }

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderID=" + orderID +
                ", orderDate='" + orderDate + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", userID=" + userID +
                ", pharmacyID=" + pharmacyID +
                ", medicineID=" + medicineID +
                ", orderType='" + orderType + '\'' +
                '}';
    }
}

