CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    student_id INT NULL
);

CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    branch VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS companies (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    package INT NOT NULL
);

CREATE TABLE IF NOT EXISTS interns (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    branch VARCHAR(100) NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    internship_role VARCHAR(100) NOT NULL,
    stipend INT NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL DEFAULT 'ONGOING'
);

CREATE TABLE IF NOT EXISTS placed_students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    original_student_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    branch VARCHAR(100) NOT NULL,
    company_id INT NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    package INT NOT NULL,
    placed_on DATE NOT NULL,
    UNIQUE KEY uq_placed_original_student (original_student_id)
);

-- For an existing old users table, run this once if the column is missing:
-- ALTER TABLE users ADD COLUMN student_id INT NULL;

INSERT INTO users (username, password, role, student_id)
VALUES ('admin', 'admin123', 'ADMIN', NULL)
ON DUPLICATE KEY UPDATE role='ADMIN';

-- Example student login after adding a student:
-- UPDATE users SET student_id = 1 WHERE username = 'student1';
-- Or create directly:
-- INSERT INTO users (username, password, role, student_id)
-- VALUES ('student1', 'student123', 'STUDENT', 1);
