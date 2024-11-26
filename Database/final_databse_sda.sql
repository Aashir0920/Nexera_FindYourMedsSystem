create database FindYourMeds_finalDB;
use FindYourMeds_finalDB;

CREATE TABLE FindYourMedsSystem (
    systemID INT AUTO_INCREMENT PRIMARY KEY,
    adminName VARCHAR(255),
    adminContact VARCHAR(255),
    adminEmail VARCHAR(255),
    password1 VARCHAR(50)
);

-- Insertion Of the Admin 
INSERT INTO FindYourMedsSystem (adminName, adminContact, adminEmail, password1)
VALUES 
('Maria', '123-456-7890', 'maria@findyourmeds.com', 'securePass123');


CREATE TABLE Customer (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    contactInfo VARCHAR(255),
    address1 VARCHAR(255),
    password1 VARCHAR(255)
);

INSERT INTO Customer (name, email, contactInfo, address1, password1) VALUES
('Azfar', 'azfar@example.com', '111-222-3333', '123 Street A', '1234'),
('Bilal', 'bilal@example.com', '444-555-6666', '456 Street B', '1234'),
('Najam', 'najam@example.com', '777-888-9999', '789 Street C', '1234');


CREATE TABLE DrugstoreOwner (
    ownerID INT AUTO_INCREMENT PRIMARY KEY,
    name1 VARCHAR(255),
    email VARCHAR(255),
    contactInfo VARCHAR(255),
    businessLicense VARCHAR(255),
    approval VARCHAR(255),
    password1 VARCHAR(255)
);

INSERT INTO DrugstoreOwner (name1, email, contactInfo, businessLicense, approval, password1) VALUES
('Aashir', 'aashir@drugstore.com', '112-334-5566', 'BL12345', 'Pending', 'davidPass'),
('Aalyan', 'alien@drugstore.com', '223-445-6677', 'BL67890', 'Pending', 'evePass'),
('Shaheer', 'shaheer@drugstore.com', '334-556-7788', 'BL98765', 'Pending', 'frankPass');


CREATE TABLE Pharmacy (
    pharmacyID INT AUTO_INCREMENT PRIMARY KEY,
    name1 VARCHAR(255),
    location1 VARCHAR(255),
    contactInfo VARCHAR(255),
    ownerID INT,
    FOREIGN KEY (ownerID) REFERENCES DrugstoreOwner(ownerID)
);



CREATE TABLE Medicine (
    medID INT AUTO_INCREMENT PRIMARY KEY,
    name1 VARCHAR(255),
    formula VARCHAR(255),
    price DECIMAL(10, 2),
    quantity INT,
    manufacturer VARCHAR(255),
    pharmacyID INT,
    FOREIGN KEY (pharmacyID) REFERENCES Pharmacy(pharmacyID)
);
CREATE TABLE Wishlist (
    reqid INT AUTO_INCREMENT PRIMARY KEY,
    userid INT,
    medid INT,
    pharmacyid INT,
    FOREIGN KEY (userid) REFERENCES Pharmacy(pharmacyid),
    FOREIGN KEY (userid) REFERENCES DrugstoreOwner(ownerID),
    FOREIGN KEY (medid) REFERENCES Medicine(medID)
);

CREATE TABLE Orders (
    orderID INT AUTO_INCREMENT PRIMARY KEY,
    orderDate DATE,
    quantity INT,
    total DECIMAL(10, 2),
    paymentStatus VARCHAR(255),
    approvalStatus VARCHAR(255),
    userID INT,
    pharmacyID INT,
    medicineID INT,
    orderType VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (userID) REFERENCES Customer(userID),
    FOREIGN KEY (pharmacyID) REFERENCES Pharmacy(pharmacyID),
    FOREIGN KEY (medicineID) REFERENCES Medicine(medID)
);
CREATE TABLE Feedback (
    feedbackID INT AUTO_INCREMENT PRIMARY KEY,
    text TEXT,
    date DATE,
    pharmacyID INT,
    FOREIGN KEY (pharmacyID) REFERENCES Pharmacy(pharmacyID)
);

ALTER TABLE Feedback
ADD COLUMN uid int;
ALTER TABLE Feedback
ADD CONSTRAINT FK_Feedback_uid
FOREIGN KEY (uid) REFERENCES Customer(userID);

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE Feedback;
TRUNCATE TABLE Orders;
TRUNCATE TABLE Wishlist;
TRUNCATE TABLE Medicine;
TRUNCATE TABLE Pharmacy;
TRUNCATE TABLE DrugstoreOwner;
TRUNCATE TABLE Customer;
TRUNCATE TABLE FindYourMedsSystem;

SET FOREIGN_KEY_CHECKS = 1;

