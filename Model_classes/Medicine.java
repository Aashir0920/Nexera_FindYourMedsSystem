package Model_classes;

public class Medicine {
    private int medID;
    private String name;
    private String formula;
    private double price;
    private int quantity;
    private String manufacturer;
    private int pharmacyID;

    // Constructor
    public Medicine(int medID, String name, String formula, double price, int quantity, String manufacturer, int pharmacyID) {
        this.medID = medID;
        this.name = name;
        this.formula = formula;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.pharmacyID = pharmacyID;
    }

    // Getters and Setters
    public int getMedID() {
        return medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medID=" + medID +
                ", name='" + name + '\'' +
                ", formula='" + formula + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", manufacturer='" + manufacturer + '\'' +
                ", pharmacyID=" + pharmacyID +
                '}';
    }
}

