package com.hospital.db;

public class DatabaseSeed {
    public static String getResetDatabaseQuery() {
        return "-- RESET DATABASE\n" +
                "DROP TABLE IF EXISTS billings, appointments, patients, doctors;\n" +

                "-- TABLES CREATE\n" +
                "-- doctors Table\n" +
                "CREATE TABLE doctors (\n" +
                "   doctor_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "   name VARCHAR(50),\n" +
                "   specialization VARCHAR(100),\n" +
                "   phone VARCHAR(50),\n" +
                "   email VARCHAR(50),\n" +
                "   department VARCHAR(100)\n" +
                ");\n" +

                "-- patients Table\n" +
                "CREATE TABLE patients (\n" +
                "   patient_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "   name VARCHAR(100),\n" +
                "   gender VARCHAR(10),\n" +
                "   age INT,\n" +
                "   phone VARCHAR(15),\n" +
                "   address TEXT,\n" +
                "   admission_date DATE\n" +
                ");\n" +

                "-- appointments Table\n" +
                "CREATE TABLE appointments (\n" +
                "   appointment_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "   patient_id INT,\n" +
                "   doctor_id INT,\n" +
                "   appointment_date DATE,\n" +
                "   appointment_time TIME,\n" +
                "   status VARCHAR(50),\n" +
                "   FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,\n" +
                "   FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE\n" +
                ");\n" +

                "-- billings Table\n" +
                "CREATE TABLE billings (\n" +
                "   bill_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "   patient_id INT,\n" +
                "   total_amount DECIMAL(10, 2),\n" +
                "   payment_status VARCHAR(50),\n" +
                "   payment_date DATE,\n" +
                "   FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE\n" +
                ");\n" +

                "-- DATA INSERT\n" +
                "-- doctors Table\n" +
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
                "('Dr. Anika Sultana', 'Nephrologist', '01710000010', 'anika.sultana@example.com', 'Nephrology');\n" +

                "-- patients Table\n" +
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
                "('Shafiq Rahman', 'Male', 55, '01711000010', 'Narayanganj', '2025-08-10');\n" +

                "-- appointments Table\n" +
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
                "(10, 10, '2025-08-14', '12:00:00', 'Scheduled');\n" +

                "-- billings Table\n" +
                "INSERT INTO billings (patient_id, total_amount, payment_status, payment_date) VALUES\n" +
                "(1, 5000.00, 'Paid', '2025-08-05'),\n" +
                "(2, 12000.00, 'Overdue', NULL),\n" +
                "(3, 8000.00, 'Paid', '2025-08-07'),\n" +
                "(4, 3000.00, 'Paid', '2025-08-08'),\n" +
                "(5, 1500.00, 'Paid', '2025-08-09'),\n" +
                "(6, 2500.00, 'Overdue', NULL),\n" +
                "(7, 9000.00, 'Paid', '2025-08-11'),\n" +
                "(8, 20000.00, 'Overdue', NULL),\n" +
                "(9, 4500.00, 'Paid', '2025-08-13'),\n" +
                "(10, 7000.00, 'Paid', '2025-08-14');";
    }
}