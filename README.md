# Student-Information-Form
This project is a Swing-based Java application that allows users to input and store student information in a MySQL database. The application includes fields for first name, last name, gender, phone number, email, university roll number, programming languages known, and CGPA. The form also performs validation on the input data to ensure accuracy before saving it to the database.

# Features
* User Interface: The application provides a simple and user-friendly graphical interface using Java Swing.
* Input Fields: Includes text fields for student details, radio buttons for gender selection, checkboxes for programming languages, and more.
* Data Validation: Ensures that all fields are correctly filled out before submission.
* Database Interaction: Connects to a MySQL database to store and retrieve student information.
* Error Handling: Handles common errors, such as duplicate entries for phone numbers or emails, and out-of-range values for roll numbers.
* Reset Functionality: Allows users to reset the form to its default state.

# Prerequisites
* Java Development Kit (JDK): Make sure you have JDK 8 or later installed.
* MySQL Database: Install XAMPP or any other MySQL server.
* JDBC Driver: Ensure you have the MySQL JDBC driver (mysql-connector-java.jar) added to your project's classpath.

# Setup Instructions
1. Clone the repository:
      git clone https://github.com/your-username/student-information-form.git
      cd student-information-form
2. Set up MySQL Database:
* Start your MySQL server.
* Create a database named studentdb.
* Create a table with the following SQL command:
    CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    gender VARCHAR(10),
    phone_no VARCHAR(15) UNIQUE,
    email VARCHAR(100) UNIQUE,
    roll_no BIGINT UNIQUE,
    programming_languages VARCHAR(255),
    cgpa DOUBLE
    );

3. Configure Database Connection:
   * Update the database connection string in the 'submitForm' method of the 'MyFrame' class:
       Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "your_password");

4. Run the Application:
   * Compile and run the 'Form.java' file in your Java IDE or from the command line:
       javac Form.java
       java Form
     
# Usage
  1. Launch the application.
  2. Fill out the student information in the provided fields.
  3. Click on the "Submit" button to save the data to the MySQL database.
  4. Use the "Reset" button to clear all input fields.

# Troubleshooting
  * Database Connection Errors: Ensure that your MySQL server is running and that the connection details are correct.
  * Data Truncation Errors: Check the data type and length of the database columns, especially for fields like roll_no.
  * Duplicate Entry Errors: The application checks for duplicate phone numbers and email addresses before inserting data.

# Contributing
  * Contributions are welcome! If you find any issues or have suggestions for improvements, feel free to create a pull request or open an issue on the GitHub repository.
