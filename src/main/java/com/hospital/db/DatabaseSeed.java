package com.hospital.db;

public class DatabaseSeed {
    public static String[] getCreateTableSQLs() {
        String[] createTableSQLs = {
                "CREATE TABLE IF NOT EXISTS users (" +
                        "    user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    username TEXT UNIQUE NOT NULL," +
                        "    password TEXT NOT NULL," +
                        "    role TEXT CHECK(role IN ('admin','doctor','receptionist','accountant')) NOT NULL," +
                        "    name TEXT," +
                        "    contact TEXT" +
                        ");",

                "CREATE TABLE IF NOT EXISTS patients (" +
                        "    patient_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    name TEXT NOT NULL," +
                        "    gender TEXT CHECK(gender IN ('Male','Female','Other'))," +
                        "    date_of_birth DATE," +
                        "    contact TEXT," +
                        "    address TEXT," +
                        "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ");",

                "CREATE TABLE IF NOT EXISTS doctors (" +
                        "    doctor_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    user_id INTEGER UNIQUE," +
                        "    name TEXT NOT NULL," +
                        "    specialization TEXT," +
                        "    contact TEXT," +
                        "    consultation_fee REAL," +
                        "    availability TEXT," +
                        "    FOREIGN KEY(user_id) REFERENCES users(user_id)" +
                        ");",

                "CREATE TABLE IF NOT EXISTS appointments (" +
                        "    appointment_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    patient_id INTEGER NOT NULL," +
                        "    doctor_id INTEGER NOT NULL," +
                        "    appointment_date TIMESTAMP NOT NULL," +
                        "    appointment_time TIMESTAMP NOT NULL," +
                        "    status TEXT CHECK(status IN ('scheduled','completed','canceled')) DEFAULT 'scheduled'," +
                        "    notes TEXT," +
                        "    prescription_code INTEGER," +
                        "    FOREIGN KEY(patient_id) REFERENCES patients(patient_id)," +
                        "    FOREIGN KEY(doctor_id) REFERENCES doctors(doctor_id)" +
                        ");",

                "CREATE TABLE IF NOT EXISTS billing (" +
                        "    bill_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    appointment_id INTEGER NOT NULL," +
                        "    amount REAL NOT NULL," +
                        "    payment_status TEXT CHECK(payment_status IN ('unpaid','paid','partial')) DEFAULT 'unpaid'," +
                        "    payment_method TEXT," +
                        "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "    FOREIGN KEY(appointment_id) REFERENCES appointments(appointment_id)" +
                        ");"
        };

        return createTableSQLs;
    }


    public static String[] getSeedData() {
        String[] seedData = {
                // ================== Users (10) ==================
                "INSERT INTO users (username, password, role, name, contact) VALUES ('acc2', '1234', 'accountant', 'Linda Brown', '555-0125');",
                "INSERT INTO users (username, password, role, name, contact) VALUES ('accounts1', '1234', 'accountant', 'David Chen', '555-0106');",
                "INSERT INTO users (username, password, role, name, contact) VALUES ('reception1', '1234', 'receptionist', 'Sarah Wilson', '555-0105');",
                "INSERT INTO users (username, password, role, name, contact) VALUES ('recep2', '1234', 'receptionist', 'Mark Taylor', '555-0124');",
                "INSERT INTO users (username, password, role, name, contact) VALUES ('drlee', '1234', 'doctor', 'Dr. Benjamin Lee', '555-0102');",
                "INSERT INTO users (username, password, role, name, contact) VALUES ('drsophia', '1234', 'doctor', 'Dr. Sophia Khan', '555-0107');",
                "INSERT INTO users (username, password, role, name, contact) VALUES ('drcarter', '1234', 'doctor', 'Dr. Emily Carter', '555-0101');",
                "INSERT INTO users (username, password, role, name, contact) VALUES ('drmichael', '1234', 'doctor', 'Dr. Michael Brown', '555-0108');",
                "INSERT INTO users (username, password, role, name, contact) VALUES ('drayesha', '1234', 'doctor', 'Dr. Ayesha Rahman', '555-0113');",

                // ================== Doctors (5) ==================
                "INSERT INTO doctors (user_id, name, specialization, contact, consultation_fee, availability) VALUES (6, 'Dr. Benjamin Lee', 'Pediatrician', '555-0102', 150.00, 'Mon,Wed,Fri 10am-6pm');",
                "INSERT INTO doctors (user_id, name, specialization, contact, consultation_fee, availability) VALUES (7, 'Dr. Sophia Khan', 'Dermatologist', '555-0107', 180.00, 'Tue-Thu 9am-3pm');",
                "INSERT INTO doctors (user_id, name, specialization, contact, consultation_fee, availability) VALUES (8, 'Dr. Emily Carter', 'Cardiologist', '555-0101', 250.00, 'Mon-Fri 9am-5pm');",
                "INSERT INTO doctors (user_id, name, specialization, contact, consultation_fee, availability) VALUES (9, 'Dr. Michael Brown', 'Orthopedic', '555-0108', 220.00, 'Mon-Sat 11am-7pm');",
                "INSERT INTO doctors (user_id, name, specialization, contact, consultation_fee, availability) VALUES (10, 'Dr. Ayesha Rahman', 'Neurologist', '555-0113', 300.00, 'Sun-Thu 10am-4pm');",

                // ================== Patients (10) ==================
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('John Smith', 'Male', '1985-05-20', '555-0103', '123 Maple St');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('Maria Garcia', 'Female', '1992-08-15', '555-0104', '456 Oak Ave');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('Ahmed Hossain', 'Male', '1990-12-02', '555-0109', '789 Pine Rd');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('Emily Johnson', 'Female', '2000-03-11', '555-0110', '321 Cedar Ln');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('David Kim', 'Male', '1978-07-19', '555-0111', '654 Birch Blvd');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('Fatima Begum', 'Female', '1988-01-25', '555-0119', '22 Lake View Rd');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('Mohammad Ali', 'Male', '1965-11-11', '555-0120', '90 Riverside Dr');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('Jessica Wong', 'Female', '1995-04-17', '555-0121', '11 Park Ave');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('Carlos Martinez', 'Male', '1982-02-08', '555-0122', '77 Hill Rd');",
                "INSERT INTO patients (name, gender, date_of_birth, contact, address) VALUES ('Amina Khatun', 'Female', '1970-06-30', '555-0123', '55 Green St');",

                // ================== Appointments (10) ==================
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (1, 1, '2025-08-28', '10:30:00', 'scheduled', 'Follow-up checkup', 101);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (2, 2, '2025-08-28', '11:00:00', 'scheduled', 'Pediatric fever consultation', 102);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (3, 3, '2025-08-29', '12:00:00', 'scheduled', 'Skin rash issue', 103);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (4, 4, '2025-08-29', '09:30:00', 'scheduled', 'Knee pain', 104);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (5, 5, '2025-08-30', '10:00:00', 'completed', 'Neurology consultation done', 105);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (6, 1, '2025-08-30', '13:30:00', 'canceled', 'Patient absent', NULL);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (7, 2, '2025-08-31', '11:15:00', 'scheduled', 'Gynecology checkup', 106);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (8, 3, '2025-08-31', '14:45:00', 'scheduled', 'ENT throat pain', 107);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (9, 4, '2025-09-01', '15:00:00', 'scheduled', 'Oncology follow-up', 108);",
                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes, prescription_code) VALUES (10, 5, '2025-09-01', '16:15:00', 'scheduled', 'Urology consultation', 109);",

                // ================== Billing (10) ==================
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (1, 250.00, 'unpaid', 'cash');",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (2, 150.00, 'paid', 'card');",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (3, 180.00, 'partial', 'mobile');",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (4, 220.00, 'paid', 'cash');",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (5, 300.00, 'paid', 'card');",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (6, 120.00, 'unpaid', NULL);",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (7, 200.00, 'paid', 'mobile');",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (8, 180.00, 'unpaid', 'cash');",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (9, 350.00, 'paid', 'card');",
                "INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES (10, 270.00, 'partial', 'mobile');"
        };

        return seedData;
    }
}