package com.hospital.db;

/**
 * Contains all SQL queries used in the application, primarily for database
 * migration.
 * The syntax has been adjusted for SQLite compatibility.
 */
public class DatabaseSeed {
    public static String getResetDatabaseQuery() {
        return "DROP TABLE IF EXISTS billings;" +
                "DROP TABLE IF EXISTS appointments;" +
                "DROP TABLE IF EXISTS patients;" +
                "DROP TABLE IF EXISTS doctors;" +

                "CREATE TABLE doctors (\n" +
                "   doctor_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   name TEXT,\n" +
                "   specialization TEXT,\n" +
                "   phone TEXT,\n" +
                "   email TEXT,\n" +
                "   department TEXT\n" +
                ");" +

                "CREATE TABLE patients (\n" +
                "   patient_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   name TEXT,\n" +
                "   gender TEXT,\n" +
                "   age INTEGER,\n" +
                "   phone TEXT,\n" +
                "   address TEXT,\n" +
                "   admission_date TEXT\n" +
                ");" +

                "CREATE TABLE appointments (\n" +
                "   appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   patient_id INTEGER,\n" +
                "   doctor_id INTEGER,\n" +
                "   appointment_date TEXT,\n" +
                "   appointment_time TEXT,\n" +
                "   status TEXT,\n" +
                "   FOREIGN KEY (patient_id) REFERENCES patients(patient_id),\n" +
                "   FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)\n" +
                ");" +

                "CREATE TABLE billings (\n" +
                "   bill_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   patient_id INTEGER,\n" +
                "   total_amount REAL,\n" +
                "   payment_status TEXT,\n" +
                "   payment_date TEXT,\n" +
                "   FOREIGN KEY (patient_id) REFERENCES patients(patient_id)\n" +
                ");" +

                "INSERT INTO doctors (name, specialization, phone, email, department) VALUES\n" +
                "('Dr. Ahsan Rahman', 'Cardiologist', '01710000001', 'ahsan.rahman@example.com', 'Cardiology'),\n" +
                "('Dr. Mahfuz Alam', 'Neurologist', '01710000002', 'mahfuz.alam@example.com', 'Neurology'),\n" +
                "('Dr. Sadia Akter', 'Orthopedic', '01710000003', 'sadia.akter@example.com', 'Orthopedics'),\n" +
                "('Dr. Kamal Uddin', 'Dermatologist', '01710000004', 'kamal.uddin@example.com', 'Dermatology'),\n" +
                "('Dr. Farhana Kabir', 'Pediatrician', '01710000005', 'farhana.kabir@example.com', 'Pediatrics'),\n" +
                "('Dr. Imran Hossain', 'ENT Specialist', '01710000006', 'imran.hossain@example.com', 'ENT'),\n" +
                "('Dr. Selina Haque', 'Gynecologist', '01710000007', 'selina.haque@example.com', 'Gynecology'),\n" +
                "('Dr. Rashed Khan', 'Oncologist', '01710000008', 'rashed.khan@example.com', 'Oncology'),\n" +
                "('Dr. Tahmid Rahman', 'Psychiatrist', '01710000009', 'tahmid.rahman@example.com', 'Psychiatry'),\n" +
                "('Dr. Anika Sultana', 'Nephrologist', '01710000010', 'anika.sultana@example.com', 'Nephrology');" +

                "INSERT INTO patients (name, gender, age, phone, address, admission_date) VALUES\n" +
                "('Rakib Hasan', 'Male', 32, '01711000001', 'Dhaka', '2025-08-01'),\n" +
                "('Nusrat Jahan', 'Female', 28, '01711000002', 'Chittagong', '2025-08-02'),\n" +
                "('Sabbir Hossain', 'Male', 45, '01711000003', 'Sylhet', '2025-08-03'),\n" +
                "('Mahmudul Hasan', 'Male', 40, '01711000004', 'Rajshahi', '2025-08-04'),\n" +
                "('Shamima Sultana', 'Female', 35, '01711000005', 'Khulna', '2025-08-05'),\n" +
                "('Tariq Anwar', 'Male', 29, '01711000006', 'Barisal', '2025-08-06'),\n" +
                "('Fahmida Akter', 'Female', 50, '01711000007', 'Comilla', '2025-08-07'),\n" +
                "('Mehedi Hasan', 'Male', 38, '01711000008', 'Mymensingh', '2025-08-08'),\n" +
                "('Rumana Akter', 'Female', 27, '01711000009', 'Gazipur', '2025-08-09'),\n" +
                "('Shafiq Rahman', 'Male', 55, '01711000010', 'Narayanganj', '2025-08-10');" +

                "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status) VALUES\n"
                +
                "(1, 1, '2025-08-05', '10:30:00', 'Completed'),\n" +
                "(2, 2, '2025-08-06', '14:00:00', 'Scheduled'),\n" +
                "(3, 3, '2025-08-07', '09:00:00', 'Completed'),\n" +
                "(4, 4, '2025-08-08', '11:00:00', 'Scheduled'),\n" +
                "(5, 5, '2025-08-09', '15:30:00', 'Completed'),\n" +
                "(6, 6, '2025-08-10', '16:00:00', 'Scheduled'),\n" +
                "(7, 7, '2025-08-11', '13:00:00', 'Completed'),\n" +
                "(8, 8, '2025-08-12', '10:00:00', 'Scheduled'),\n" +
                "(9, 9, '2025-08-13', '09:30:00', 'Completed'),\n" +
                "(10, 10, '2025-08-14', '12:00:00', 'Scheduled');" +

                "INSERT INTO billings (patient_id, total_amount, payment_status, payment_date) VALUES\n" +
                "(1, 5000.00, 'Paid', '2025-08-05'),\n" +
                "(2, 12000.00, 'Unpaid', NULL),\n" +
                "(3, 8000.00, 'Paid', '2025-08-07'),\n" +
                "(4, 3000.00, 'Paid', '2025-08-08'),\n" +
                "(5, 1500.00, 'Paid', '2025-08-09'),\n" +
                "(6, 2500.00, 'Unpaid', NULL),\n" +
                "(7, 9000.00, 'Paid', '2025-08-11'),\n" +
                "(8, 20000.00, 'Unpaid', NULL),\n" +
                "(9, 4500.00, 'Paid', '2025-08-13'),\n" +
                "(10, 7000.00, 'Paid', '2025-08-14');";
    }
}