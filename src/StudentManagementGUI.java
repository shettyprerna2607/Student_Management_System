import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Student class to store student details
class Student {
    String name;
    int rollno;
    float marks;

    // Constructor to initialize student
    public Student(String name, int rollno, float marks) {
        this.name = name;
        this.rollno = rollno;
        this.marks = marks;
    }

    // To display student info as string
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollno + ", Marks: " + marks;
    }
}

// Main GUI class for student management
public class StudentManagementGUI {
    private JFrame frame;
    private JTextField nameField, rollField, marksField, searchField;
    private DefaultTableModel tableModel;
    private ArrayList<Student> studentList = new ArrayList<>(); // To store all students

    // Constructor to create GUI
    public StudentManagementGUI() {
        frame = new JFrame("Student Management System"); // Create window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // 1. Top Panel: for adding new student
        JPanel topPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        nameField = new JTextField();
        rollField = new JTextField();
        marksField = new JTextField();
        JButton addButton = new JButton("Add Student");

        // Add input fields and button to top panel
        topPanel.add(new JLabel("Name:"));
        topPanel.add(nameField);
        topPanel.add(new JLabel("Roll No:"));
        topPanel.add(rollField);
        topPanel.add(new JLabel("Marks:"));
        topPanel.add(marksField);
        topPanel.add(addButton);

        // 2. Center Panel: table to show students
        String[] columns = {"Name", "Roll No", "Marks"};
        tableModel = new DefaultTableModel(columns, 0); // Table with 3 columns
        JTable studentTable = new JTable(tableModel);   // Create table
        JScrollPane scrollPane = new JScrollPane(studentTable); // Add scroll bar

        // 3. Bottom Panel: for searching, deleting, and showing topper
        JPanel bottomPanel = new JPanel(new FlowLayout());

        searchField = new JTextField(10); // For roll number input
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton topperButton = new JButton("Show Topper");

        // Add components to bottom panel
        bottomPanel.add(new JLabel("Roll No:"));
        bottomPanel.add(searchField);
        bottomPanel.add(searchButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(topperButton);

        // Add all panels to main frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addStudent());     // Add student
        searchButton.addActionListener(e -> searchStudent()); // Search student
        deleteButton.addActionListener(e -> deleteStudent()); // Delete student
        topperButton.addActionListener(e -> showTopper());     // Show topper

        frame.setVisible(true); // Show window
    }

    // Function to add a new student
    private void addStudent() {
        try {
            String name = nameField.getText();
            int roll = Integer.parseInt(rollField.getText());
            float marks = Float.parseFloat(marksField.getText());

            Student s = new Student(name, roll, marks);
            studentList.add(s); // Add to list
            tableModel.addRow(new Object[]{name, roll, marks}); // Add to table

            // Clear input fields after adding
            nameField.setText("");
            rollField.setText("");
            marksField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Enter valid inputs!");
        }
    }

    // Function to search a student by roll number
    private void searchStudent() {
        String input = searchField.getText();
        try {
            int roll = Integer.parseInt(input);
            for (Student s : studentList) {
                if (s.rollno == roll) {
                    JOptionPane.showMessageDialog(frame, "Found: " + s);
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Student not found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Enter a valid roll number.");
        }
    }

    // Function to delete a student by roll number
    private void deleteStudent() {
        String input = searchField.getText();
        try {
            int roll = Integer.parseInt(input);
            for (int i = 0; i < studentList.size(); i++) {
                if (studentList.get(i).rollno == roll) {
                    studentList.remove(i);       // Remove from list
                    tableModel.removeRow(i);     // Remove from table
                    JOptionPane.showMessageDialog(frame, "Student deleted.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Student not found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Enter a valid roll number.");
        }
    }

    // Function to show the student with the highest marks
    private void showTopper() {
        if (studentList.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No students to evaluate.");
            return;
        }

        Student topper = studentList.get(0); // Start with first student
        for (Student s : studentList) {
            if (s.marks > topper.marks) {
                topper = s;
            }
        }
        JOptionPane.showMessageDialog(frame, "Topper: " + topper);
    }

    // Main method to start the app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}
