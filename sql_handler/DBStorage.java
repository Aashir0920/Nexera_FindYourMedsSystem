package sql_handler;
import controller.OwnerLoginResult;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DBStorage {
	private static DBStorage instance;
    private Connection connection;
 // Database connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/FindYourMeds_finalDB";
    private final String USER = "root";
    private final String PASSWORD = "rootroot";
    private DBStorage() throws SQLException {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found!", e);
        }
    }
    public static DBStorage getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBStorage();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBStorage();
        }
        return instance;
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }
 // Close the connection when not needed
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean validateCustomer(String name, String password) {
        String query = "SELECT COUNT(*) FROM Customer WHERE name = ? AND password1 = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if a matching record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Returns false if no matching record is found
    }
    public ResultSet executeQuery(String query, String... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);

        // Set parameters if any
        for (int i = 0; i < params.length; i++) {
            statement.setString(i + 1, params[i]);
        }

        // Execute query and return the result
        return statement.executeQuery();
    }
    
    public int executeUpdate(String query, Object... params) throws SQLException {
        // Prepare the statement
        PreparedStatement statement = connection.prepareStatement(query);
        
        // Set parameters dynamically
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]); // Set each parameter
        }

        // Execute the update (this will work for INSERT, UPDATE, DELETE)
        return statement.executeUpdate();
    }
    
    public boolean validateAdmin(String username, String password) {
        String query = "SELECT * FROM FindYourMedsSystem WHERE adminName = ? AND password1 = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            return rs.next(); // Returns true if a matching record is found
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Returns false if no matching record is found
    }

    public List<String> getUnapprovedOwners() throws SQLException {
        String query = "SELECT ownerID, name1, businessLicense, approval " +
                       "FROM DrugstoreOwner WHERE approval = 'pending'";
        List<String> unapprovedOwners = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Populate the list with formatted owner details
            while (resultSet.next()) {
                int ownerID = resultSet.getInt("ownerID");
                String name = resultSet.getString("name1");
                String businessLicense = resultSet.getString("businessLicense");
                String approvalStatus = resultSet.getString("approval");

                String ownerDetails = String.format("ID: %d - Name: %s - License: %s - Status: %s",
                        ownerID, name, businessLicense, approvalStatus);
                unapprovedOwners.add(ownerDetails);
            }
        }

        return unapprovedOwners;
    }

    public boolean approveOwner(int ownerID) throws SQLException {
        String updateQuery = "UPDATE DrugstoreOwner SET approval = 'approved' WHERE ownerID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, ownerID);

            // Returns true if at least one row was updated
            return statement.executeUpdate() > 0;
        }
    }

    public int getCustomerUserID(String username, String password) throws SQLException {
        String query = "SELECT userID FROM Customer WHERE name = ? AND password1 = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("userID"); // Return the userID if found
            }
        }
        return -1; // Return -1 if no matching record is found
    }

    public boolean registerCustomer(String fullName, String email, String contact, String address, String password) throws SQLException {
        String query = "INSERT INTO Customer (name, email, contactInfo, address1, password1) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, contact);
            pstmt.setString(4, address);
            pstmt.setString(5, password);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row was inserted
        }
    }

    
    public List<String> getFeedbackDetails() throws SQLException {
        String query = "SELECT c.name AS customerName, p.name1 AS pharmacyName, f.text AS feedbackText " +
                       "FROM Feedback f " +
                       "INNER JOIN Pharmacy p ON f.pharmacyID = p.pharmacyID " +
                       "INNER JOIN Customer c ON c.userID = f.uid";
        List<String> feedbackList = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Populate the list with formatted feedback details
            while (resultSet.next()) {
                String customerName = resultSet.getString("customerName");
                String pharmacyName = resultSet.getString("pharmacyName");
                String feedbackText = resultSet.getString("feedbackText");
                
                String feedbackDetails = String.format("Customer: %s - Pharmacy: %s - Feedback: %s",
                        customerName, pharmacyName, feedbackText);
                feedbackList.add(feedbackDetails);
            }
        }
        return feedbackList;
    }

    public boolean addMedicine(String name, String formula, double price, int quantity, String manufacturer, int pharmacyID) throws SQLException {
        String query = "INSERT INTO Medicine (name1, formula, price, quantity, manufacturer, pharmacyID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, formula);
            statement.setDouble(3, price);
            statement.setInt(4, quantity);
            statement.setString(5, manufacturer);
            statement.setInt(6, pharmacyID);

            return statement.executeUpdate() > 0; // Return true if the insertion was successful
        }
    }

    public boolean addPharmacy(String name, String location, String contact, String ownerId) throws SQLException {
        String query = "INSERT INTO Pharmacy (name1, location1, contactInfo, ownerID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, location);
            statement.setString(3, contact);
            statement.setString(4, ownerId);

            return statement.executeUpdate() > 0; // Return true if the insertion was successful
        }
    }

    public List<String> getFeedbackForOwner(int ownerID) throws SQLException {
        String query = """
            SELECT F.text, F.date, P.name1 AS pharmacy_name
            FROM Feedback F
            JOIN Pharmacy P ON F.pharmacyID = P.pharmacyID
            WHERE P.ownerID = ?;
        """;

        List<String> feedbackList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ownerID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String feedback = String.format(
                        "Pharmacy: %s\nDate: %s\nFeedback: %s",
                        resultSet.getString("pharmacy_name"),
                        resultSet.getDate("date"),
                        resultSet.getString("text")
                    );
                    feedbackList.add(feedback);
                }
            }
        }
        return feedbackList;
    }
    
    public List<List<String>> getMedicinesByPharmacyID(int pharmacyID) throws SQLException {
        String query = "SELECT name1, formula, price, quantity, manufacturer FROM Medicine WHERE pharmacyID = ?";
        List<List<String>> medicines = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pharmacyID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    List<String> medicine = new ArrayList<>();
                    medicine.add(resultSet.getString("name1"));
                    medicine.add(resultSet.getString("formula"));
                    medicine.add(String.valueOf(resultSet.getDouble("price")));
                    medicine.add(String.valueOf(resultSet.getInt("quantity")));
                    medicine.add(resultSet.getString("manufacturer"));
                    medicines.add(medicine);
                }
            }
        }
        return medicines;
    }

    public boolean deleteMedicine(int pharmacyID, String medicineName) throws SQLException {
        String query = "DELETE FROM Medicine WHERE name1 = ? AND pharmacyID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicineName);
            statement.setInt(2, pharmacyID);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateMedicineStock(int pharmacyID, String medicineName, int quantity) throws SQLException {
        String query = "UPDATE Medicine SET quantity = ? WHERE name1 = ? AND pharmacyID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setString(2, medicineName);
            statement.setInt(3, pharmacyID);
            return statement.executeUpdate() > 0;
        }
    }
    
 // Load pharmacies for the logged-in owner
    public ObservableList<ObservableList<String>> getPharmaciesByOwner(String ownerId) throws SQLException {
        String query = "SELECT name1, location1, contactInfo FROM Pharmacy WHERE ownerID = ?";
        ObservableList<ObservableList<String>> pharmacies = FXCollections.observableArrayList();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ownerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ObservableList<String> pharmacy = FXCollections.observableArrayList(
                        resultSet.getString("name1"),
                        resultSet.getString("location1"),
                        resultSet.getString("contactInfo")
                );
                pharmacies.add(pharmacy);
            }
        }
        return pharmacies;
    }

    // Get pharmacy ID by name and owner ID
    public int getPharmacyId(String pharmacyName, String ownerId) throws SQLException {
        String query = "SELECT pharmacyID FROM Pharmacy WHERE name1 = ? AND ownerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pharmacyName);
            statement.setString(2, ownerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("pharmacyID");
            }
        }
        return -1; // Return -1 if not found
    }

    // Delete a pharmacy and its associated medicines
    public boolean deletePharmacyAndMedicines(String pharmacyName, String ownerId) throws SQLException {
        String queryGetPharmacyID = "SELECT pharmacyID FROM Pharmacy WHERE name1 = ? AND ownerID = ?";
        String queryDeleteMedicines = "DELETE FROM Medicine WHERE pharmacyID = ?";
        String queryDeletePharmacy = "DELETE FROM Pharmacy WHERE pharmacyID = ?";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            // Get pharmacy ID
            int pharmacyID;
            try (PreparedStatement stmtGetPharmacyID = connection.prepareStatement(queryGetPharmacyID)) {
                stmtGetPharmacyID.setString(1, pharmacyName);
                stmtGetPharmacyID.setString(2, ownerId);
                ResultSet rs = stmtGetPharmacyID.executeQuery();

                if (rs.next()) {
                    pharmacyID = rs.getInt("pharmacyID");
                } else {
                    connection.rollback();
                    return false;
                }
            }

            // Delete medicines
            try (PreparedStatement stmtDeleteMedicines = connection.prepareStatement(queryDeleteMedicines)) {
                stmtDeleteMedicines.setInt(1, pharmacyID);
                stmtDeleteMedicines.executeUpdate();
            }

            // Delete pharmacy
            try (PreparedStatement stmtDeletePharmacy = connection.prepareStatement(queryDeletePharmacy)) {
                stmtDeletePharmacy.setInt(1, pharmacyID);
                stmtDeletePharmacy.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    // Fetch orders for a specific drugstore owner
    public ArrayList<String> fetchOrdersForOwner(int ownerID) {
        ArrayList<String> orders = new ArrayList<>();
        try {
            Connection connection = getConnection();
            String query = """
                SELECT o.orderID, o.orderDate, o.total, o.approvalStatus, c.name 
                FROM Orders o
                JOIN Pharmacy p ON o.pharmacyID = p.pharmacyID
                JOIN Customer c ON o.userID = c.userID
                WHERE p.ownerID = ?;
            """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ownerID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderID");
                String date = resultSet.getString("orderDate");
                double total = resultSet.getDouble("total");
                String status = resultSet.getString("approvalStatus");
                String customerName = resultSet.getString("name");

                // Format the order details for display
                String orderDetails = String.format(
                    "Order #%d - %s - %s - $%.2f - %s",
                    orderId, customerName, date, total, status
                );
                orders.add(orderDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Update the approval status of an order
    public boolean approveOrder(int orderId) {
        try {
            Connection connection = getConnection();
            String query = "UPDATE Orders SET approvalStatus = 'Approved' WHERE orderID = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public OwnerLoginResult validateLoginAndGetApprovalStatus(String username, String password) {
        String query = "SELECT ownerID, approval FROM DrugstoreOwner WHERE name1 = ? AND password1 = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String ownerId = resultSet.getString("ownerID");
                String approvalStatus = resultSet.getString("approval");
                return new OwnerLoginResult(ownerId, approvalStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if query fails or no matching record is found
    }
    
    public int registerDrugstoreOwner(String name, String email, String contactInfo, String businessLicense, String password) throws SQLException {
        String query = "INSERT INTO DrugstoreOwner (name1, email, contactInfo, businessLicense, approval, password1) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, contactInfo);
            statement.setString(4, businessLicense);
            statement.setString(5, "Pending"); // default approval status
            statement.setString(6, password);

            return statement.executeUpdate();
        }
    }
    
    public int getPharmacyIDByName(String pharmacyName) {
        int pharmacyID = -1;
        String query = "SELECT pharmacyID FROM Pharmacy WHERE name1 = ?";

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, pharmacyName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pharmacyID = rs.getInt("pharmacyID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pharmacyID;
    }

    /**
     * Inserts feedback into the database.
     *
     * @param userID     the ID of the user submitting the feedback
     * @param pharmacyID the ID of the pharmacy
     * @param feedbackText the feedback text
     * @return true if the feedback was successfully inserted, false otherwise
     */
    public boolean insertFeedback(int userID, int pharmacyID, String feedbackText) {
        String query = "INSERT INTO Feedback (text, date, pharmacyID, uid) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, feedbackText);
            pst.setDate(2, Date.valueOf(java.time.LocalDate.now())); // current date
            pst.setInt(3, pharmacyID);
            pst.setInt(4, userID);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ObservableList<ObservableList<String>> fetchOrders(int userID) {
        ObservableList<ObservableList<String>> orders = FXCollections.observableArrayList();

        String query = "SELECT o.orderID, o.medicineID, o.paymentStatus, o.approvalStatus, o.orderType " +
                       "FROM Orders o WHERE o.userID = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ObservableList<String> orderData = FXCollections.observableArrayList(
                        String.valueOf(rs.getInt("orderID")),      // Order ID
                        String.valueOf(rs.getInt("medicineID")),   // Medicine ID
                        rs.getString("paymentStatus"),             // Payment Status
                        rs.getString("approvalStatus"),            // Approval Status
                        rs.getString("orderType")                  // Order Type
                );
                orders.add(orderData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        PreparedStatement pst = connection.prepareStatement(query);
        setParameters(pst, params);
        return pst.executeQuery();
    }

    public int executeUpdate1(String query, Object... params) throws SQLException {
        PreparedStatement pst = connection.prepareStatement(query);
        setParameters(pst, params);
        return pst.executeUpdate();
    }

    private void setParameters(PreparedStatement pst, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pst.setObject(i + 1, params[i]);
        }
    }

    public List<String> searchMedicines(String searchQuery) throws SQLException {
        String query = "SELECT m.name1, p.location1, p.pharmacyID FROM Medicine m " +
                       "JOIN Pharmacy p ON m.pharmacyID = p.pharmacyID WHERE m.name1 LIKE ?";
        List<String> medicines = new ArrayList<>();
        ResultSet rs = executeQuery(query, "%" + searchQuery + "%");

        while (rs.next()) {
            String medicineName = rs.getString("name1");
            String pharmacyLocation = rs.getString("location1");
            int pharmacyID = rs.getInt("pharmacyID");
            medicines.add(medicineName + " - Available at: " + pharmacyLocation + " (Pharmacy ID = " + pharmacyID + ")");
        }
        return medicines;
    }

    public ResultSet getMedicineDetails(String medicineName) throws SQLException {
        String query = "SELECT medID, price FROM Medicine WHERE name1 = ?";
        return executeQuery(query, medicineName);
    }

    public void placeOrder(int userID, int pharmacyID, int medicineID, int quantity, double total) throws SQLException {
        String query = "INSERT INTO Orders (orderDate, total, quantity, paymentStatus, approvalStatus, userID, pharmacyID, medicineID) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        executeUpdate1(query, LocalDate.now().toString(), total, quantity, "Pending", "Pending", userID, pharmacyID, medicineID);
    }

    public void addToWishlist(int userID, int medicineID, int pharmacyID) throws SQLException {
        String query = "INSERT INTO Wishlist (userid, medid, pharmacyid) VALUES (?, ?, ?)";
        executeUpdate(query, userID, medicineID, pharmacyID);
    }

    
}
