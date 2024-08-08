import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class MyFrame extends JFrame {
    JButton b1, b2;
    JCheckBox c1, c2, c3, c4;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8;
    JTextField t1, t2, t3, t4, t5, t6;
    JRadioButton rb1, rb2;

    public MyFrame() {
        super("Student Information");

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Initialize components
        l1 = new JLabel("First Name");
        t1 = new JTextField(15);  // Reduced width
        l2 = new JLabel("Last Name");
        t2 = new JTextField(15);  // Reduced width
        l3 = new JLabel("Gender");
        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        l4 = new JLabel("Phone No");
        t3 = new JTextField(15);  // Reduced width
        l5 = new JLabel("E-Mail");
        t4 = new JTextField(15);  // Reduced width
        l6 = new JLabel("University Roll No");
        t5 = new JTextField(15);  // Reduced width
        l7 = new JLabel("Programming Languages:");
        c1 = new JCheckBox("Java");
        c2 = new JCheckBox("C++");
        c3 = new JCheckBox("Python");
        c4 = new JCheckBox("C");
        l8 = new JLabel("CGPA till 6th Sem");
        t6 = new JTextField(15);  // Reduced width
        b1 = new JButton("Submit");
        b2 = new JButton("Reset");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);

        // Arrange components on the grid
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(l1, gbc);
        gbc.gridx = 1;
        add(t1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(l2, gbc);
        gbc.gridx = 1;
        add(t2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(l3, gbc);
        gbc.gridx = 1;
        add(rb1, gbc);
        gbc.gridx = 2;
        add(rb2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(l4, gbc);
        gbc.gridx = 1;
        add(t3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(l5, gbc);
        gbc.gridx = 1;
        add(t4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(l6, gbc);
        gbc.gridx = 1;
        add(t5, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(l7, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        add(c1, gbc);
        gbc.gridy = 7;
        add(c2, gbc);
        gbc.gridy = 8;
        add(c3, gbc);
        gbc.gridy = 9;
        add(c4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        add(l8, gbc);
        gbc.gridx = 1;
        add(t6, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        add(b1, gbc);

        gbc.gridx = 2;
        add(b2, gbc);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void submitForm() {
        String firstName = t1.getText();
        String lastName = t2.getText();
        String gender = rb1.isSelected() ? "Male" : rb2.isSelected() ? "Female" : "";
        String phoneNo = t3.getText();
        String email = t4.getText();
        String rollNo = t5.getText();
        String programmingLanguages = "";
        if (c1.isSelected()) programmingLanguages += "Java ";
        if (c2.isSelected()) programmingLanguages += "C++ ";
        if (c3.isSelected()) programmingLanguages += "Python ";
        if (c4.isSelected()) programmingLanguages += "C ";
        programmingLanguages = programmingLanguages.trim();
        String cgpaStr = t6.getText();

        // Validate form fields
        if (firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty() || phoneNo.isEmpty() || email.isEmpty() ||
                rollNo.isEmpty() || programmingLanguages.isEmpty() || cgpaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out!");
            return;
        }

        double cgpa;
        try {
            cgpa = Double.parseDouble(cgpaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "CGPA must be a number!");
            return;
        }

        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentdb", "root", "");

            // Check if email already exists
            String checkEmailQuery = "SELECT COUNT(*) FROM students WHERE email = ?";
            PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailQuery);
            checkEmailStmt.setString(1, email);
            ResultSet rs = checkEmailStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Email is already used!");
                checkEmailStmt.close();
                conn.close();
                return;
            }
            checkEmailStmt.close();

            // Check if phone number already exists
            String checkPhoneQuery = "SELECT COUNT(*) FROM students WHERE phone_no = ?";
            PreparedStatement checkPhoneStmt = conn.prepareStatement(checkPhoneQuery);
            checkPhoneStmt.setString(1, phoneNo);
            ResultSet rsPhone = checkPhoneStmt.executeQuery();
            rsPhone.next();
            int phoneCount = rsPhone.getInt(1);
            if (phoneCount > 0) {
                JOptionPane.showMessageDialog(this, "Phone number is already used!");
                checkPhoneStmt.close();
                conn.close();
                return;
            }
            checkPhoneStmt.close();

            // Prepare SQL query
            String sql = "INSERT INTO students (first_name, last_name, gender, phone_no, email, roll_no, programming_languages, cgpa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, gender);
            pstmt.setString(4, phoneNo);
            pstmt.setString(5, email);
            pstmt.setString(6, rollNo);
            pstmt.setString(7, programmingLanguages);
            pstmt.setDouble(8, cgpa);

            // Execute the query
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data saved successfully!");

            // Close connection
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
        }
    }

    private void resetForm() {
        t1.setText("");
        t2.setText("");
        rb1.setSelected(false);
        rb2.setSelected(false);
        t3.setText("");
        t4.setText("");
        t5.setText("");
        c1.setSelected(false);
        c2.setSelected(false);
        c3.setSelected(false);
        c4.setSelected(false);
        t6.setText("");
    }
}

public class Form {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyFrame::new);
    }
}
